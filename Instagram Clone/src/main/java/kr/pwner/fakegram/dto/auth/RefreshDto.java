package kr.pwner.fakegram.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RefreshDto {
    @Data
    @Accessors(chain = true)
    public static class Request {
        @NotBlank(message="refreshToken field is mandatory")
        @Pattern(regexp="(^[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*$)",
                message = "Invalid Token Format")
        private String refreshToken;
    }
    @Data
    @Accessors(chain = true)
    public static class Response {
        private String accessToken;
    }
}