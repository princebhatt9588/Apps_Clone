package kr.pwner.fakegram.controller;

import kr.pwner.fakegram.dto.ApiResponse.SuccessResponse;
import kr.pwner.fakegram.service.FeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.type.NullType;

@RequestMapping(path = "/api/v1/feed")
@RestController
public class FeedController {
    FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse<NullType>> CreateFeed(
            @RequestHeader(name = "Authorization") final String authorization,
            @RequestParam(name="files", required = false) MultipartFile[] files,
            @RequestParam(name="content", required= false) String content
    ) {
        feedService.CreateFeed(authorization, files, content);
        return new ResponseEntity<>(new SuccessResponse<>(), HttpStatus.OK);
    }
}
