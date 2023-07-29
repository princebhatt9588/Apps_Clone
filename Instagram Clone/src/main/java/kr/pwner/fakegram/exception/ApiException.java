package kr.pwner.fakegram.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class ApiException extends RuntimeException{
    private final String code;
    private final HttpStatus status;
    private final Date timestamp;
    private final String description;

    public ApiException(ExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.status = exceptionEnum.getStatus();
        this.timestamp = new Date();
        this.description = exceptionEnum.getDescription();
    }
}
