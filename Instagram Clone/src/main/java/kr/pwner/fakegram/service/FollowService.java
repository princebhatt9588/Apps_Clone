package kr.pwner.fakegram.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import kr.pwner.fakegram.dto.follow.FollowDto;
import kr.pwner.fakegram.exception.ApiException;
import kr.pwner.fakegram.exception.ExceptionEnum;
import kr.pwner.fakegram.model.Follow;
import kr.pwner.fakegram.repository.AccountRepository;
import kr.pwner.fakegram.repository.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class FollowService {
    FollowRepository followRepository;
    AccountRepository accountRepository;
    JwtService jwtService;

    public FollowService(
            FollowRepository followRepository,
            AccountRepository accountRepository,
            JwtService jwtService
    ) {
        this.followRepository = followRepository;
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public void Follow(String authorization, FollowDto.Request request) {
        DecodedJWT accessToken = jwtService.VerifyAccessToken(
                authorization.replace("Bearer ", "")
        );
        // The access token will be verified on the interceptor
        Long fromIdx = accessToken.getClaim("idx").asLong();
        Long toIdx = Optional.ofNullable(accountRepository.findByIdAndIsActivateTrue(request.getTargetId()))
                .orElseThrow(() -> new ApiException(ExceptionEnum.ACCOUNT_NOT_EXISTS)).getIdx();

        if (Objects.equals(fromIdx, toIdx))
            throw new ApiException(ExceptionEnum.CANNOT_FOLLOW_YOURSELF);

        Follow followHistory = followRepository.findByFromIdxAndToIdx(fromIdx, toIdx);

        if (Objects.isNull(followHistory)) { // do follow
            Follow follow = Follow.builder()
                    .fromIdx(fromIdx)
                    .toIdx(toIdx)
                    .build();
            followRepository.save(follow);
        } else { // unfollow
            followRepository.deleteByIdx(followHistory.getIdx());
        }
    }
}
