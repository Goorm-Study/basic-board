package study.basic_board.dto;

import lombok.Getter;
import study.basic_board.entity.User;

@Getter
// Response랑 Request 나눠야할 듯?
// request : 클라이언트에서 서버로 데이터 전송
public class BoardRequestDto {
    private User user;
    private String title;
    private String content;
}
