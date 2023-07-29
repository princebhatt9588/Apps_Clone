package kr.pwner.fakegram.exception;

import kr.pwner.fakegram.dto.ApiResponse.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.ArrayList;
import java.util.HashMap;

@RestControllerAdvice
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> DtoValidationExceptionHandler(MethodArgumentNotValidException exception) {
        ArrayList<HashMap<String, String>> errorList = new ArrayList<>();
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("field", fieldError.getField());
            errorMap.put("description", fieldError.getDefaultMessage());
            errorList.add(errorMap);
        }
        ErrorResponse apiErrorResponse = new ErrorResponse(new ApiException(ExceptionEnum.INVALID_REQUEST_BODY));
        apiErrorResponse.setErrors(errorList);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

//    @ExceptionHandler(IllegalArgumentException.class)

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> ApiExceptionHandler(ApiException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler({MissingServletRequestPartException.class, MultipartException.class})
    public ResponseEntity<ErrorResponse> MultipartException(){
        ErrorResponse errorResponse = new ErrorResponse(new ApiException(ExceptionEnum.INVALID_MULTIPART_REQUEST));
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}