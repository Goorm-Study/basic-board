package study.basic_board.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final View error;

    public ApiExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionResponse> handleApiException(ApiException e) {
        ErrorType errorType = e.getErrorType();
        ApiExceptionResponse response = new ApiExceptionResponse(
                errorType.getHttpStatus().value(),
                errorType.getErrorCode(),
                errorType.getMsg()
        );

        return ResponseEntity.status(errorType.getHttpStatus().value()).body(response);
    }
}
