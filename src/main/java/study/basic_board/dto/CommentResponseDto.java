package study.basic_board.dto;


import lombok.Data;
import lombok.Getter;
import study.basic_board.entity.Comment;

import java.time.LocalDateTime;

@Data
@Getter
public class CommentResponseDto {
    private String username;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // comment의 정보를 이용하여 dto 생성
    public CommentResponseDto(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.createTime = comment.getCreateTime();
        this.updateTime = comment.getUpdateTime();
    }
}
