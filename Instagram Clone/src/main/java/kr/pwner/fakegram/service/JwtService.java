package kr.pwner.fakegram.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import kr.pwner.fakegram.model.Account;
import kr.pwner.fakegram.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {
    private final AccountRepository accountRepository;

    public JwtService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Value("${env.JWT_ACCESS_SECRET}")
    private String accessTokenSecret;

    @Value("${env.JWT_REFRESH_SECRET}")
    private String refreshTokenSecret;

    // * 15 minutes
    public long getAccessTokenExpiresIn(){
        return 1000 * 60 * 15 + new Date().getTime();
    }
    // * 1 day
    public long getRefreshTokenExpiresIn(){
        return 1000 * 60 * 60 * 24 + new Date().getTime();
    }

    public DecodedJWT VerifyAccessToken(final String token) {
        return JWT.require(Algorithm.HMAC256(accessTokenSecret))
                .build()
                .verify(token);
    }
    public DecodedJWT VerifyRefreshToken(final String token) {
        return JWT.require(Algorithm.HMAC256(refreshTokenSecret))
                .build()
                .verify(token);
    }

    public String GenerateAccessToken(final String id) {
        Account account = accountRepository.findByIdAndIsActivateTrue(id);
        return  JWT.create()
                .withExpiresAt(new Date(getAccessTokenExpiresIn()))
                .withClaim("idx", account.getIdx())
                .sign(Algorithm.HMAC256(accessTokenSecret));
    }

    @Transactional
    public String GenerateRefreshToken(final String id) {
        Account account = accountRepository.findByIdAndIsActivateTrue(id);
        account.SignIn(UUID.randomUUID().toString());
        return JWT.create()
                .withExpiresAt(new Date(getRefreshTokenExpiresIn()))
                .withClaim("idx", account.getIdx())
                .withClaim("refreshToken", account.getRefreshToken())
                .sign(Algorithm.HMAC256(refreshTokenSecret));
    }
}