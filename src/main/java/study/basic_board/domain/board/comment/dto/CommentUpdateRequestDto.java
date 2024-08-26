package study.basic_board.domain.board.comment.dto;


import lombok.Getter;

@Getter
// inner, outer 기능 찾아서,
// 생성할 때, 수정할 때 구분해주는 기능?
public class CommentUpdateRequestDto {
    private Long userId;
    private Long commentId; // 생성할 때는 필요 없음!
    private String content;
}
