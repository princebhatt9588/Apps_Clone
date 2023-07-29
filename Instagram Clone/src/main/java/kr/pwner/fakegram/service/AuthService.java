package kr.pwner.fakegram.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import kr.pwner.fakegram.dto.auth.RefreshDto;
import kr.pwner.fakegram.dto.auth.SignInDto;
import kr.pwner.fakegram.exception.ApiException;
import kr.pwner.fakegram.exception.ExceptionEnum;
import kr.pwner.fakegram.model.Account;
import kr.pwner.fakegram.repository.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public AuthService(
            final AccountRepository accountRepository,
            final BCryptPasswordEncoder bCryptPasswordEncoder,
            final JwtService jwtService
    ) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    private Account ValidateAccount(final String id, final String password) {
        Account account = Optional.ofNullable(accountRepository.findByIdAndIsActivateTrue(id))
                .orElseThrow(() -> new ApiException(ExceptionEnum.ACCOUNT_NOT_EXISTS));
        if (!bCryptPasswordEncoder.matches(password, account.getPassword()))
            throw new ApiException(ExceptionEnum.INCORRECT_ACCOUNT_PASSWORD);
        return account;
    }

    public SignInDto.Response SignIn(final SignInDto.Request request) {
        Account account = ValidateAccount(request.getId(), request.getPassword());
        SignInDto.Response response = new SignInDto.Response();
        response.setAccessTokenExpiresIn(String.valueOf(jwtService.getAccessTokenExpiresIn() / 1000))
                .setRefreshTokenExpiresIn(String.valueOf(jwtService.getRefreshTokenExpiresIn() / 1000))
                .setAccessToken(jwtService.GenerateAccessToken(account.getId()))
                .setRefreshToken(jwtService.GenerateRefreshToken(account.getId()));
        return response;
    }

    @Transactional(readOnly = true)
    public RefreshDto.Response Refresh(final RefreshDto.Request request) {
        DecodedJWT refreshToken;
        try {
            // ? It's not validated on the interceptor because refreshToken is passed via body
            refreshToken = jwtService.VerifyRefreshToken(
                    request.getRefreshToken().replace("Bearer ", "")
            );
        } catch (NullPointerException | JWTDecodeException e) {
            throw new ApiException(ExceptionEnum.INVALID_OR_EXPIRED_TOKEN);
        }
        Long idx = refreshToken.getClaim("idx").asLong();
        Account account = Optional.ofNullable(accountRepository.findByIdxAndIsActivateTrue(idx))
                .orElseThrow(() -> new ApiException(ExceptionEnum.ACCOUNT_NOT_EXISTS));

        String dbRefreshToken = account.getRefreshToken();
        String RequestRefreshToken = refreshToken.getClaim("refreshToken").asString();

        if (!Objects.equals(dbRefreshToken, RequestRefreshToken))
            throw new ApiException(ExceptionEnum.INVALID_OR_EXPIRED_TOKEN);

        String accessToken = jwtService.GenerateAccessToken(account.getId());
        return new RefreshDto.Response().setAccessToken(accessToken);
    }

    @Transactional
    public void SignOut(String authorization) {
        DecodedJWT accessToken = jwtService.VerifyAccessToken(
                authorization.replace("Bearer ", "")
        );
        Long idx = accessToken.getClaim("idx").asLong();
        Account account = accountRepository.findByIdxAndIsActivateTrue(idx);
        Optional.ofNullable(account.getRefreshToken())
                .orElseThrow(() -> new ApiException(ExceptionEnum.ALREADY_SIGN_OUT));
        account.SignOut();
    }
}