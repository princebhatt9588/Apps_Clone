package kr.pwner.fakegram.controller;

import kr.pwner.fakegram.dto.ApiResponse.SuccessResponse;
import kr.pwner.fakegram.dto.account.CreateAccountDto;
import kr.pwner.fakegram.dto.account.ReadAccountDto;
import kr.pwner.fakegram.dto.account.UpdateAccountDto;
import kr.pwner.fakegram.service.AccountService;
import kr.pwner.fakegram.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.type.NullType;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SuccessResponse<NullType>> CreateAccount(
            @Valid @RequestBody final CreateAccountDto.Request request
    ) {
        accountService.CreateAccount(request);
        return new ResponseEntity<>(new SuccessResponse<>(), HttpStatus.OK);

    }

    @RequestMapping(value = "/{id:^[a-zA-Z0-9]+}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<SuccessResponse<ReadAccountDto.Response>> ReadAccount(
            @Valid @PathVariable final String id
    ) {
        ReadAccountDto.Response response = accountService.ReadAccount(id);
        return new ResponseEntity<>(new SuccessResponse<>(response), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<SuccessResponse<NullType>> UpdateAccount(
            @RequestHeader(name = "Authorization") final String authorization,
            @Valid @RequestBody final UpdateAccountDto.Request request
    ) {
        accountService.UpdateAccount(authorization, request);
        return new ResponseEntity<>(new SuccessResponse<>(), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<SuccessResponse<NullType>> DeleteAccount(
            @RequestHeader(name = "Authorization") final String authorization
    ) {
        accountService.DeleteAccount(authorization);
        return new ResponseEntity<>(new SuccessResponse<>(), HttpStatus.OK);
    }

    @RequestMapping(value = "/upload/profileImage", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SuccessResponse<String>> UploadProfileImage(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestParam("file") MultipartFile file
    ) {
        String fileName= accountService.UploadProfileImage(authorization, file);
        return new ResponseEntity<>(new SuccessResponse<>(UploadService.getFileUri(fileName)), HttpStatus.OK);
    }
}