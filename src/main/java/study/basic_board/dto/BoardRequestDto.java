package study.basic_board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.basic_board.entity.User;


// @Data에는 setter가 포함되어있어서 사용 X
@Getter
@NoArgsConstructor
// Response랑 Request 나눠야할 듯?
// request : 클라이언트에서 서버로 데이터 전송
// inner, outer 기능 찾아서,
// 생성할 때, 수정할 때 구분해주는 기능?
public class BoardRequestDto {
    private Long userId;
    private String title;
    private String content;
}
