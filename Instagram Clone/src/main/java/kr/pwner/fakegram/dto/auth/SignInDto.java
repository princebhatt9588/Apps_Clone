package kr.pwner.fakegram.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignInDto {
    @Data
    @Accessors(chain=true)
    public static class Request {
        @NotBlank(message = "id field is mandatory")
        @Size(min = 4, max = 20, message = "id field must be between 4 and 20 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+", message = "^[a-zA-Z0-9]+")
        private String id;

        @NotBlank(message = "password field is mandatory")
        // Minimum eight characters, at least one letter and one number
        @Size(min = 8, max = 30, message = "password field must be between 8 and 30 characters")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+", message = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+")
        private String password;
    }
    @Data
    @Accessors(chain=true)
    public static class Response {
        private String accessTokenExpiresIn;
        private String refreshTokenExpiresIn;
        private String accessToken;
        private String refreshToken;
    }
}
