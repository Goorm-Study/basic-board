package study.basic_board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.basic_board.dto.BoardDto;

import java.time.LocalDateTime;
import java.util.List;

@Entity // DB 테이블
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 유저가 여러 게시물(필요할 때 로딩 - LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;



    // 글 등록할 때 쓰는 생성자
    public Board(User user, BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
        this.createdTime = LocalDateTime.now();
        this.user = user;
    }
}
