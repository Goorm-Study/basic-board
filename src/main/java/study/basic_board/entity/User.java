package study.basic_board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.basic_board.dto.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자는 막기
public class User {
    @Id // primary key
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Board> boards;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

    // 유저 등록할 때 생성자
    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.birth = userDto.getBirth();
        this.nickname = userDto.getNickname();
        this.createdTime = LocalDateTime.now();
    }

    // 유저 정보 수정할 때 생성자
    public void updateUser(UserDto userDto) {
        this.username = userDto.getUsername();
        this.nickname = userDto.getNickname();
        this.modifiedTime = LocalDateTime.now();
    }

}
