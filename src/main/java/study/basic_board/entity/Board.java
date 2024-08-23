package study.basic_board.entity;

import jakarta.persistence.*;
import lombok.*;
import study.basic_board.base.BaseTimeEntity;
import study.basic_board.dto.board.BoardCreateRequestDto;
import study.basic_board.dto.board.BoardUpdateRequestDto;

import java.util.List;

@Entity // DB 테이블
@Getter
// @Builder // 유연성, entity를 깔끔하게 할 수 있다.
// @AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 이 클래스에는 @Builder를 쓰는 것이 적합하지 않음.
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> comments;
    // MappedBy를 위해서 양방향으로 설정함!

    @ManyToOne(fetch = FetchType.LAZY) // 하나의 유저가 여러 게시물(필요할 때 로딩 - LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    // 글 등록할 때 쓰는 생성자
    // 생성자에도 User를 파라미터로 받으면 안되는건가?
    @Builder
    // 이렇게 빌더를 적용하면 등록할 때, 수정할 때 상황에 맞게 쓸 수 있음. -> 어차피 id는 자동 생성이니까!
    public Board(User user, Long id, String title, String content) {
        this.user = user;
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // 글 수정할 때 쓰는 생성자 -> 필요 없어짐 -> 다시 필요 있어짐
    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.title = boardUpdateRequestDto.getTitle();
        this.content = boardUpdateRequestDto.getContent();
    }

/*
    <gpt의 의견>
    **엔티티 재생성 대신 기존 엔티티 사용**
    현재 코드에서는 boardId를 기반으로 새로운 Board 객체를 빌더 패턴으로 생성하고 있습니다. 그러나, 이 방법은 효율적이지 않으며, 데이터베이스의 다른 필드가 유지되지 않을 수 있는 문제가 있습니다.
    기존 엔티티를 로드한 후 그 엔티티의 필드만 업데이트하는 것이 더 적절합니다. 이렇게 하면 엔티티의 나머지 필드가 유지되고, JPA의 더 나은 변경 추적 기능을 활용할 수 있습니다.
*/
}
