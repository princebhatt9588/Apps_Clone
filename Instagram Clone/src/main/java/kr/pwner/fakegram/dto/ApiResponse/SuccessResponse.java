package kr.pwner.fakegram.dto.ApiResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse <T> {
    private final String message = "Success";
    private T data;
    public SuccessResponse(T data){
        this.data = data;
    }
    public SuccessResponse(){}
}
