package kr.pwner.fakegram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pwner.fakegram.dto.ApiResponse.SuccessResponse;
import kr.pwner.fakegram.dto.account.CreateAccountDto;
import kr.pwner.fakegram.dto.account.ReadAccountDto;
import kr.pwner.fakegram.dto.account.UpdateAccountDto;
import kr.pwner.fakegram.dto.follow.FollowDto;
import kr.pwner.fakegram.model.Account;
import kr.pwner.fakegram.repository.AccountRepository;
import kr.pwner.fakegram.repository.FollowRepository;
import kr.pwner.fakegram.service.AccountService;
import kr.pwner.fakegram.service.FollowService;
import kr.pwner.fakegram.service.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(AccountController.class) - use for unit test
@AutoConfigureMockMvc
@SpringBootTest
public class AccountControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FollowService followService;
    @Autowired
    private FollowRepository followRepository;

    private final String BASE_URL = "/api/v1/account";

    private final String TESTER_ID_0 = "TeSteR";
    private final String TESTER_ID_1 = TESTER_ID_0 + "shit";

    private final String TESTER_PW = "password123";
    private final String TESTER_EMAIL = "testtest@test.com";
    private final String TESTER_NAME = "tester!";

    private String TESTER_0_ACCESS_TOKEN;

    @BeforeEach
    public void init() {
        CreateAccountDto.Request request = new CreateAccountDto.Request()
                .setId(TESTER_ID_0)
                .setPassword(TESTER_PW)
                .setEmail(TESTER_EMAIL)
                .setName(TESTER_NAME);
        accountService.CreateAccount(request);

        request.setId(TESTER_ID_1);
        accountService.CreateAccount(request);

        TESTER_0_ACCESS_TOKEN = jwtService.GenerateAccessToken(TESTER_ID_0);
    }

    @AfterEach
    public void done() {
        accountRepository.deleteById(TESTER_ID_0);
        accountRepository.deleteById(TESTER_ID_1);
    }

    @Test
    public void CreateAccount() throws Exception {
        CreateAccountDto.Request request = new CreateAccountDto.Request()
                .setId(TESTER_ID_0 + "123")
                .setPassword(TESTER_PW)
                .setEmail(TESTER_EMAIL)
                .setName(TESTER_NAME);

        mvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        String accountId = accountRepository.findById(TESTER_ID_0 + "123").getId();
        assertEquals(TESTER_ID_0 + "123", accountId);

        accountRepository.deleteById(TESTER_ID_0 + "123");
    }

    @Test
    public void ReadAccount() throws Exception {
        FollowDto.Request followDto = new FollowDto.Request().setTargetId(TESTER_ID_0);
        followService.Follow(jwtService.GenerateAccessToken(TESTER_ID_1), followDto);

        Long accountIdx = accountRepository.findById(TESTER_ID_0).getIdx();
        FollowDto.Response follow = new FollowDto.Response();
        follow.setFollower(followRepository.getFollowerByIdx(accountIdx));
        follow.setFollowing(followRepository.getFollowingByIdx(accountIdx));

        SuccessResponse<ReadAccountDto.Response> successResponse = new SuccessResponse<>(
                new ReadAccountDto.Response()
                        .setId(TESTER_ID_0)
                        .setName(TESTER_NAME)
                        .setEmail(TESTER_EMAIL)
                        .setProfilePicture("")
                        .setFollow(follow)
        );

        mvc.perform(get(BASE_URL + "/" + TESTER_ID_0))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(successResponse)));
        followRepository.deleteAllByIdx(accountIdx);
    }

    @Test
    public void UpdateAccount() throws Exception {
        UpdateAccountDto.Request request = new UpdateAccountDto.Request()
                .setId(TESTER_ID_0 + "1234")
                .setPassword(TESTER_PW + "123")
                .setEmail("asd" + TESTER_EMAIL)
                .setName(TESTER_NAME + "123");

        mvc.perform(put(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, TESTER_0_ACCESS_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Account account = accountRepository.findById(TESTER_ID_0 + "1234");
        assertEquals(TESTER_ID_0 + "1234", account.getId());
        assertEquals("asd" + TESTER_EMAIL, account.getEmail());
        assertEquals(TESTER_NAME + "123", account.getName());

        accountRepository.deleteById(TESTER_ID_0 + "1234");
    }

    @Test
    public void DeleteAccount() throws Exception {
        mvc.perform(delete(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, TESTER_0_ACCESS_TOKEN)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        Account account = accountRepository.findById(TESTER_ID_0);

        assertFalse(account.getIsActivate());
    }

    @Test
    public void UploadProfileImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "shit.jpg",
                "image/png",
                new FileInputStream("./src/test/java/kr/pwner/fakegram/image/image.jpg")
        );

        mvc.perform(multipart(BASE_URL + "/upload/profileImage")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION, TESTER_0_ACCESS_TOKEN))
                .andExpect(status().isOk());
    }
}