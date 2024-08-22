package study.basic_board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    ILLEGAL_DATA(HttpStatus.BAD_REQUEST, "위반 데이터"),
    NOT_BLANK(HttpStatus.BAD_REQUEST, "필수 값이 누락되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
