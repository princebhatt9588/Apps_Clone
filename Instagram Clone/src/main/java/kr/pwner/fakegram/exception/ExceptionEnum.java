package kr.pwner.fakegram.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    ACCOUNT_ALREADY_EXISTS("000", "Account Already Exists", HttpStatus.CONFLICT),
    NOTHING_INFORMATION_TO_UPDATE("001", "Nothing Information Provided to Update", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_EXISTS("002", "Account not Exists", HttpStatus.NOT_FOUND),

    INCORRECT_ACCOUNT_PASSWORD("003", "Incorrect Account Password", HttpStatus.NOT_ACCEPTABLE),
    INVALID_OR_EXPIRED_TOKEN("004", "Invalid or Expired Token", HttpStatus.UNAUTHORIZED),
    ALREADY_SIGN_OUT("005", "Already Sign Out", HttpStatus.NOT_FOUND),
    ACCESS_TOKEN_REQUIRED("006", "Access Token not Provided", HttpStatus.BAD_REQUEST),

    INVALID_FILE_NAME("007", "Invalid File Name", HttpStatus.NOT_ACCEPTABLE),
    EMPTY_FILE("008", "File is Empty", HttpStatus.NOT_ACCEPTABLE),
    INVALID_MULTIPART_REQUEST("009", "Invalid Multipart Request", HttpStatus.BAD_REQUEST),
    COULD_NOT_SAVE_THE_FILE("010", "Could not Save the File", HttpStatus.INTERNAL_SERVER_ERROR),
    UNSUPPORTED_IMAGE_FORMAT("011", "Unsupported Image Format(.jpg, .png)", HttpStatus.NOT_ACCEPTABLE),

    CANNOT_FOLLOW_YOURSELF("012", "Cannot Follow Yourself", HttpStatus.BAD_REQUEST),
    FILE_OR_CONTENT_IS_MANDATORY("013", "The File or Content is Mandatory", HttpStatus.BAD_REQUEST),

    // Request Validation
    INVALID_REQUEST_BODY("100", "Invalid Request Body",HttpStatus.BAD_REQUEST),
    INVALID_PATH_VARIABLE("101", "Invalid Path Variable", HttpStatus.BAD_REQUEST)
    ;

    private final String code;
    private final HttpStatus status;
    private final String description;

    ExceptionEnum(String code, String description, HttpStatus statusCode) {
        this.code = code;
        this.status = statusCode;
        this.description = description;
    }
}
