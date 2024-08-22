package study.basic_board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.basic_board.base.BaseTimeEntity;
import study.basic_board.dto.comment.CommentCreateRequestDto;
import study.basic_board.dto.comment.CommentUpdateRequestDto;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    // 댓글 등록할 때 쓰는 생성자
    public Comment(Board board, User user, CommentCreateRequestDto commentCreateRequestDto) {
        this.user = user;
        this.content = commentCreateRequestDto.getContent();
        this.board = board;
    }

    // 댓글 수정할 때 쓰는 생성자
    public void update(CommentUpdateRequestDto commentUpdateRequestDto) {
        this.content = commentUpdateRequestDto.getContent();
    }
}
