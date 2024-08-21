package study.basic_board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.basic_board.base.BaseTimeEntity;
import study.basic_board.dto.CommentRequestDto;

import java.time.LocalDateTime;

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
    public Comment(Board board, User user, CommentRequestDto commentRequestDto) {
        this.user = user;
        this.content = commentRequestDto.getContent();
        this.board = board;
    }

}
