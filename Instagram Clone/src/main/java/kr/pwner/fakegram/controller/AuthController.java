package kr.pwner.fakegram.controller;

import kr.pwner.fakegram.dto.ApiResponse.SuccessResponse;
import kr.pwner.fakegram.dto.auth.RefreshDto;
import kr.pwner.fakegram.dto.auth.SignInDto;
import kr.pwner.fakegram.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import javax.validation.Valid;

@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SuccessResponse<SignInDto.Response>> SignIn(
            @Valid @RequestBody final SignInDto.Request request
    ) {
        SignInDto.Response response = authService.SignIn(request);
        return new ResponseEntity<>(new SuccessResponse<>(response), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<SuccessResponse<RefreshDto.Response>> Refresh(
            @Valid @RequestBody final RefreshDto.Request request
    ) {
        RefreshDto.Response response = authService.Refresh(request);
        return new ResponseEntity<>(new SuccessResponse<>(response), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<SuccessResponse<NullType>> SignOut(
            @RequestHeader(name = "Authorization") final String authorization
    ) {
        authService.SignOut(authorization);
        return new ResponseEntity<>(new SuccessResponse<>(), HttpStatus.OK);
    }
}
