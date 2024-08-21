package study.basic_board.dto;

import lombok.Getter;
import study.basic_board.entity.Board;

import java.time.LocalDateTime;

@Getter
// Response랑 Request 나눠야할 듯?
// response : 서버에서 클라이언트로 데이터 전송
public class BoardResponseDto {
    private String username;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    // board의 정보를 이용하여 dto 생성하는 생성자
    public BoardResponseDto(Board board) {
        this.username = board.getUser().getUsername();
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
