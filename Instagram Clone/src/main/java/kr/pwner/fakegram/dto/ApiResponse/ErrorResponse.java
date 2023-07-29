package kr.pwner.fakegram.dto.ApiResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.pwner.fakegram.exception.ApiException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final String code;
    private final HttpStatus status;
    private final Date timestamp;
    private final String description;

    @Setter
    private List<HashMap<String, String>> errors;       // for DTO

    // ApiException
    public ErrorResponse(ApiException apiException){
        this.code = apiException.getCode();
        this.status = apiException.getStatus();
        this.timestamp = apiException.getTimestamp();
        this.description = apiException.getDescription();
    }
}
