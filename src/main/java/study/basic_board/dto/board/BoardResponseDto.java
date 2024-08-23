package study.basic_board.dto.board;

import lombok.Getter;
import study.basic_board.entity.Board;

import java.time.LocalDateTime;

@Getter
// Response랑 Request 나눠야할 듯?
// response : 서버에서 클라이언트로 데이터 전송
public class BoardResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    // board의 정보를 이용하여 dto 생성하는 생성자
    public BoardResponseDto(Board board) {
        this.username = board.getUser().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createTime = board.getCreateTime();
        this.updateTime = board.getUpdateTime();
    }
}
