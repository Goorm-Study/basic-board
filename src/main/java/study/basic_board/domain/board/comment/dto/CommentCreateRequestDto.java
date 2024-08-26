package study.basic_board.domain.board.comment.dto;


import lombok.Getter;

@Getter
// inner, outer 기능 찾아서,
// 생성할 때, 수정할 때 구분해주는 기능?
public class CommentCreateRequestDto {
    private Long userId;
    private String content;
}
