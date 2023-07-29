package kr.pwner.fakegram.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import kr.pwner.fakegram.exception.ApiException;
import kr.pwner.fakegram.exception.ExceptionEnum;
import kr.pwner.fakegram.model.Account;
import kr.pwner.fakegram.model.Feed;
import kr.pwner.fakegram.repository.AccountRepository;
import kr.pwner.fakegram.repository.FeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Objects;

// ToDo: Implement feed pagination and upload feed(with images)
@Service
public class FeedService {
    private JwtService jwtService;
    private AccountRepository accountRepository;
    private FeedRepository feedRepository;

    public FeedService(
            JwtService jwtService,
            AccountRepository accountRepository,
            FeedRepository feedRepository
    ) {
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
        this.feedRepository = feedRepository;
    }

    @Transactional
    public void CreateFeed(String authorization, MultipartFile[] files, String content) {
        DecodedJWT decodedJWT = jwtService.VerifyAccessToken(
                authorization.replace("Bearer ", "")
        );
        if (Objects.isNull(files) && Objects.isNull(content))
            throw new ApiException(ExceptionEnum.FILE_OR_CONTENT_IS_MANDATORY);

        Long accountIdx = decodedJWT.getClaim("idx").asLong();
        Account account = accountRepository.findByIdxAndIsActivateTrue(accountIdx);
        Feed feed = Feed.builder().content(content).build();
        feed.LinkAccount(account);
        feedRepository.save(feed);

    }
}
