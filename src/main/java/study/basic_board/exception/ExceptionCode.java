package study.basic_board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    ILLEGAL_DATA(HttpStatus.BAD_REQUEST, "위반 데이터");

    private final HttpStatus httpStatus;
    private final String message;
}
