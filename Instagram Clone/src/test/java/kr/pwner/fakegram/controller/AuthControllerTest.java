package kr.pwner.fakegram.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pwner.fakegram.dto.ApiResponse.SuccessResponse;
import kr.pwner.fakegram.dto.account.CreateAccountDto;
import kr.pwner.fakegram.dto.auth.RefreshDto;
import kr.pwner.fakegram.dto.auth.SignInDto;
import kr.pwner.fakegram.model.Account;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AccountRepository accountRepository;

    private final String BASE_URL = "/api/v1/auth";

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
    public void done() {
        accountRepository.deleteById(TESTER_ID);
    }

    @Test
    public void SignIn() throws Exception {
        SignInDto.Request request = new SignInDto.Request()
                .setId(TESTER_ID)
                .setPassword(TESTER_PW);

        String response = mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // * https://stackoverflow.com/questions/11664894/jackson-deserialize-using-generic-class
        SuccessResponse<SignInDto.Response> successResponse = objectMapper.readValue(
                response, new TypeReference<>() {
                });

        DecodedJWT decodedJWT = jwtService.VerifyAccessToken(successResponse.getData().getAccessToken());

        assertEquals(
                decodedJWT.getClaim("idx").asLong(),
                accountRepository.findById(TESTER_ID).getIdx()
        );
    }

    @Test
    public void Refresh() throws Exception {
        String refreshToken = jwtService.GenerateRefreshToken(TESTER_ID);
        RefreshDto.Request request = new RefreshDto.Request().setRefreshToken(refreshToken);

        String response = mvc.perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        SuccessResponse<RefreshDto.Response> successResponse = objectMapper.readValue(
                response, new TypeReference<>() {
                });
        DecodedJWT decodedJWT = jwtService.VerifyAccessToken(successResponse.getData().getAccessToken());

        Long idx = decodedJWT.getClaim("idx").asLong();
        Account account = accountRepository.findByIdxAndIsActivateTrue(idx);

        assertEquals(idx, account.getIdx());
    }

    @Test
    public void SignOut() throws Exception {
        String accessToken = jwtService.GenerateAccessToken(TESTER_ID);
        jwtService.GenerateRefreshToken(TESTER_ID); // for generate refresh token uuid
        mvc.perform(delete(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}