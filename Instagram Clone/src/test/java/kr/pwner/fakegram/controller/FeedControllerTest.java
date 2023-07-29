package kr.pwner.fakegram.controller;

import kr.pwner.fakegram.dto.account.CreateAccountDto;
import kr.pwner.fakegram.model.Feed;
import kr.pwner.fakegram.repository.AccountRepository;
import kr.pwner.fakegram.repository.FeedRepository;
import kr.pwner.fakegram.service.AccountService;
import kr.pwner.fakegram.service.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FeedControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FeedRepository feedRepository;

    private final String BASE_URL = "/api/v1/feed";

    private final String TESTER_ID = "TeSteR";
    private final String TESTER_PW = "password123";
    private final String TESTER_EMAIL = "testtest@test.com";
    private final String TESTER_NAME = "tester!";

    @BeforeEach
    public void init() {
        CreateAccountDto.Request request = new CreateAccountDto.Request()
                .setId(TESTER_ID)
                .setPassword(TESTER_PW)
                .setEmail(TESTER_EMAIL)
                .setName(TESTER_NAME);
        accountService.CreateAccount(request);
    }

    @AfterEach
    public void done(){
        Long accountIdx = accountRepository.findById(TESTER_ID).getIdx();
        feedRepository.deleteByAccountIdx(accountIdx);
        accountRepository.deleteById(TESTER_ID);
    }

    @Test
    public void CreateFeed() throws Exception {
        String accessToken = jwtService.GenerateAccessToken(TESTER_ID);
        String content = "abcdefg";
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .param("content", content))
                .andExpect(status().isOk())
                .andDo(print());
        List<Feed> feeds = accountRepository.findById(TESTER_ID).getFeeds(); //WOW!
        assertEquals(content, feeds.get(0).getContent());
    }
}