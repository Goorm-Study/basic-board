package study.basic_board.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.basic_board.domain.board.entity.Board;
import study.basic_board.domain.board.comment.entity.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 근데 로그인 기능이 없는데,
// 어떤 유저인 줄 어떻게 알고, 글이랑 댓글을 작성할 수 있게 하지??

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자는 막기
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public User(String username, String nickname, LocalDate birth) {
        this.username = username;
        this.nickname = nickname;
        this.birth = birth;
    }

    public void updateUser(String username, String nickname, LocalDate birth) {
        this.username = username;
        this.nickname = nickname;
        this.birth = birth;
    }

}
