package kr.pwner.fakegram.dto.account;

import kr.pwner.fakegram.dto.follow.FollowDto;
import lombok.Data;
import lombok.experimental.Accessors;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReadAccountDto {
    @Data
    @Accessors(chain = true)
    public static class Response {
        private String id;
        private String name;
        private String email;
        private String profilePicture;
        private FollowDto.Response follow;
    }
}
