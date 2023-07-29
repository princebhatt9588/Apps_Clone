package kr.pwner.fakegram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pwner.fakegram.dto.account.CreateAccountDto;
import kr.pwner.fakegram.dto.follow.FollowDto;
import kr.pwner.fakegram.repository.AccountRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FollowControllerTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String TESTER_ID_0 = "TeSteR";
    private final String TESTER_ID_1 = TESTER_ID_0 + "shit";
    private final String TESTER_PW = "password123";
    private final String TESTER_EMAIL = "testtest@test.com";
    private final String TESTER_NAME = "tester!";

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
    }
    @AfterEach
    public void done(){
        accountRepository.deleteById(TESTER_ID_0);
        accountRepository.deleteById(TESTER_ID_1);
    }

    @Test
    public void Follow() throws Exception {
        String accessToken = jwtService.GenerateAccessToken(TESTER_ID_0);
        FollowDto.Request request = new FollowDto.Request().setTargetId(TESTER_ID_1);
        mvc.perform(post("/api/v1/follow") //follow
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        
        mvc.perform(post("/api/v1/follow") //unfollow
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}