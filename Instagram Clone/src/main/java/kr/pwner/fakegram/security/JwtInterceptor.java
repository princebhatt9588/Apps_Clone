package kr.pwner.fakegram.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pwner.fakegram.dto.ApiResponse.ErrorResponse;
import kr.pwner.fakegram.exception.ApiException;
import kr.pwner.fakegram.exception.ExceptionEnum;
import kr.pwner.fakegram.model.Account;
import kr.pwner.fakegram.repository.AccountRepository;
import kr.pwner.fakegram.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class JwtInterceptor implements HandlerInterceptor, Cloneable {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private List<String> excludeMethodList = Arrays.asList("");

    public JwtInterceptor(
            JwtService jwtService,
            ObjectMapper objectMapper,
            AccountRepository accountRepository
    ) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
    }

    public HandlerInterceptor setExcludeMethodList(List<String> excludeMethodList) {
        this.excludeMethodList = excludeMethodList;
        return this;
    }

    private void HttpServletApiResponse(
            final HttpServletResponse response,
            final ErrorResponse errorResponse
    ) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) throws IOException {
        if (excludeMethodList.contains(request.getMethod()))
            return true;
        try {
            String authorization = request.getHeader("Authorization");

            DecodedJWT decodedJWT = jwtService.VerifyAccessToken(
                    authorization.replace("Bearer ", "")
            );
            Account account = accountRepository.findByIdxAndIsActivateTrue(
                    decodedJWT.getClaim("idx").asLong()
            );
            if (Objects.isNull(account))
                throw new ApiException(ExceptionEnum.ACCOUNT_NOT_EXISTS);
            return true;
        } catch (JWTVerificationException e) {
            HttpServletApiResponse(response, new ErrorResponse(new ApiException(
                    ExceptionEnum.INVALID_OR_EXPIRED_TOKEN
            )));
        } catch (NullPointerException e) {
            HttpServletApiResponse(response, new ErrorResponse(new ApiException(
                    ExceptionEnum.ACCESS_TOKEN_REQUIRED
            )));
        }
        return false;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}