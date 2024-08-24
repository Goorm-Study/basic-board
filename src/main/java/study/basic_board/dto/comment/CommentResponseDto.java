package study.basic_board.dto.comment;


import lombok.Getter;
import study.basic_board.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String username;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // comment의 정보를 이용하여 dto 생성
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.createTime = comment.getCreateTime();
        this.updateTime = comment.getUpdateTime();
    }
}
