package study.basic_board.dto;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CommentRequestDto {
    private Long commentId;
    private String content;
}
