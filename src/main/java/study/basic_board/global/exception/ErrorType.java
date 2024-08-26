package study.basic_board.global.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@ToString
public enum ErrorType {

    // =========================== User ============================//
    _DUPLICATE_NICKNAME(BAD_REQUEST, "U4001", "중복되는 아이디가 존재합니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String msg;

    ErrorType(HttpStatus httpStatus, String errorCode, String msg) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}
