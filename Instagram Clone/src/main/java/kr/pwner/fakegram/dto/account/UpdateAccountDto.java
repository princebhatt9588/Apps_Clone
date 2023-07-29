package kr.pwner.fakegram.dto.account;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateAccountDto {
    @Data
    @Accessors(chain = true)
    public static class Request {
        @Size(min = 4, max = 20, message = "id field must be between 4 and 20 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+", message = "^[a-zA-Z0-9]+")
        private String id;

        // Minimum eight characters, at least one letter and one number
        @Size(min = 8, max = 30, message = "password field must be between 8 and 30 characters")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+", message = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+")
        private String password;

        @Size(min = 4, max = 20, message = "name field must be between 4 and 20 characters")
        private String name;

        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        private String email;
    }
}
