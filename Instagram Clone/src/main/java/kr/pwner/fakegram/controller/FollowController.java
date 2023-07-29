package kr.pwner.fakegram.controller;

import kr.pwner.fakegram.dto.ApiResponse.SuccessResponse;
import kr.pwner.fakegram.dto.follow.FollowDto;
import kr.pwner.fakegram.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import javax.validation.Valid;

@RequestMapping(path = "/api/v1/follow")
@RestController
public class FollowController {
    FollowService followService;
    public FollowController(final FollowService followService){
        this.followService = followService;
    }
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SuccessResponse<NullType>> Follow(
            @RequestHeader(name = "Authorization") final String authorization,
            @Valid @RequestBody final FollowDto.Request request
    ) {
        followService.Follow(authorization, request);
        return new ResponseEntity<>(new SuccessResponse<>(), HttpStatus.OK);
    }
}
