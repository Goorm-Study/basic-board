package study.basic_board.entity;

import jakarta.persistence.*;
import lombok.*;
import study.basic_board.base.BaseTimeEntity;
import study.basic_board.dto.user.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// 근데 로그인 기능이 없는데,
// 어떤 유저인 줄 어떻게 알고, 글이랑 댓글을 작성할 수 있게 하지??

@Entity
@Table(name = "users")
@Getter
//@Builder
//@AllArgsConstructor(access = AccessLevel.PROTECTED) // 이거 말고 필요한 것만 넣어서 빌더
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자는 막기
public class User extends BaseTimeEntity {
    @Id // primary key
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, unique = true)
    private String nickname;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    // 유저 등록할 때 생성자
    @Builder
    // 이렇게 빌더를 적용하면 등록할 때, 수정할 때 상황에 맞게 쓸 수 있음.
    public User(Long id, String username, LocalDate birth, String nickname) {
        this.id = id;
        this.username = username;
        this.birth = birth;
        this.nickname = nickname;
    }

    // 유저 정보 수정할 때 메서드 -> 필요 없어짐. -> 다시 필요 있어짐
    public void update(UserDto userDto) {
        this.username = userDto.getUsername();
        this.nickname = userDto.getNickname();
    }

}
