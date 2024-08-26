package study.basic_board.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;
import study.basic_board.domain.base.BaseTimeEntity;
import study.basic_board.domain.user.entity.User;
import study.basic_board.domain.board.dto.BoardCreateRequestDto;
import study.basic_board.domain.board.dto.BoardUpdateRequestDto;
import study.basic_board.domain.board.comment.entity.Comment;

import java.util.List;

@Entity // DB 테이블
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;
    // MappedBy를 위해서 양방향으로 설정함!

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 유저가 여러 게시물(필요할 때 로딩 - LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    // 글 등록할 때 쓰는 생성자
    public Board(User user, BoardCreateRequestDto boardCreateRequestDto) {
        this.title = boardCreateRequestDto.getTitle();
        this.content = boardCreateRequestDto.getContent();
        this.user = user;
    }

    // 글 수정할 때 쓰는 생성자
    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.title = boardUpdateRequestDto.getTitle();
        this.content = boardUpdateRequestDto.getContent();
    }
}
