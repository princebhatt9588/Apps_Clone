package kr.pwner.fakegram.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import kr.pwner.fakegram.dto.account.CreateAccountDto;
import kr.pwner.fakegram.dto.account.ReadAccountDto;
import kr.pwner.fakegram.dto.account.UpdateAccountDto;
import kr.pwner.fakegram.dto.follow.FollowDto;
import kr.pwner.fakegram.exception.ApiException;
import kr.pwner.fakegram.exception.ExceptionEnum;
import kr.pwner.fakegram.model.Account;
import kr.pwner.fakegram.repository.AccountRepository;
import kr.pwner.fakegram.repository.FollowRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final FollowRepository followRepository;
    private final UploadService uploadService;

    public AccountService(
            AccountRepository accountRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtService jwtService,
            FollowRepository followRepository,
            UploadService uploadService
    ) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.followRepository = followRepository;
        this.uploadService = uploadService;
    }

    public void CreateAccount(final CreateAccountDto.Request signUpDto) {
        if (Objects.nonNull(accountRepository.findById(signUpDto.getId())))
            throw new ApiException(ExceptionEnum.ACCOUNT_ALREADY_EXISTS);

        Account account = Account.builder()
                .id(signUpDto.getId())
                .password(bCryptPasswordEncoder.encode(signUpDto.getPassword()))
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .build();
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public ReadAccountDto.Response ReadAccount(final String id) {
        Account account = Optional.ofNullable(accountRepository.findByIdAndIsActivateTrue(id))
                .orElseThrow(() -> new ApiException(ExceptionEnum.ACCOUNT_NOT_EXISTS));

        FollowDto.Response follow = new FollowDto.Response();
        follow.setFollower(followRepository.getFollowerByIdx(account.getIdx()));
        follow.setFollowing(followRepository.getFollowingByIdx(account.getIdx()));

        String profileImage = Objects.isNull(account.getProfileImage()) ?
                "" : UploadService.getFileUri(account.getProfileImage());

        return new ReadAccountDto.Response()
                .setId(account.getId())
                .setName(account.getName())
                .setEmail(account.getEmail())
                .setProfilePicture(profileImage)
                .setFollow(follow);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void UpdateAccount(final String authorization, final UpdateAccountDto.Request request) {
        DecodedJWT accessToken = jwtService.VerifyAccessToken(
                authorization.replace("Bearer ", "")
        );

        if (Objects.isNull(request.getId()) &&
                Objects.isNull(request.getPassword()) &&
                Objects.isNull(request.getEmail()) &&
                Objects.isNull(request.getName())
        ) throw new ApiException(ExceptionEnum.NOTHING_INFORMATION_TO_UPDATE);

        Long idx = accessToken.getClaim("idx").asLong();
        Account account = Optional.ofNullable(accountRepository.findByIdxAndIsActivateTrue(idx))
                .orElseThrow(() -> new ApiException(ExceptionEnum.ACCOUNT_NOT_EXISTS));
        account.Update(request);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void DeleteAccount(final String authorization) {
        DecodedJWT accessToken = jwtService.VerifyAccessToken(
                authorization.replace("Bearer ", "")
        );

        Long idx = accessToken.getClaim("idx").asLong();
        Account account = Optional.ofNullable(accountRepository.findByIdxAndIsActivateTrue(idx))
                .orElseThrow(() -> new ApiException(ExceptionEnum.ACCOUNT_NOT_EXISTS));
        account.Delete();
        // ? Delete all related follow relationship
        followRepository.deleteAllByIdx(account.getIdx());
    }

    @Transactional(rollbackFor = {Exception.class})
    public String UploadProfileImage(final String authorization, final MultipartFile file){
        DecodedJWT accessToken = jwtService.VerifyAccessToken(
                authorization.replace("Bearer ", "")
        );

        // * Check image file have a valid format
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(!Objects.equals(fileExtension, "jpg") && !Objects.equals(fileExtension, "png"))
            throw new ApiException(ExceptionEnum.UNSUPPORTED_IMAGE_FORMAT);

        Account account = accountRepository.findByIdxAndIsActivateTrue(accessToken.getClaim("idx").asLong());
        String fileName = this.uploadService.SaveFile(file);
        account.ProfileImage(fileName);
        return UploadService.getFileUri(fileName);
    }
}