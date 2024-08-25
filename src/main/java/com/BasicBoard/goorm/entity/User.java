package com.BasicBoard.goorm.entity;

import com.BasicBoard.goorm.application.dto.UserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, unique = true)
    private String nickname;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Board> boards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Comment> comments;

    @Builder
    public User(String username, String nickname, LocalDate birth) {
        this.username = username;
        this.nickname = nickname;
        this.birth = birth;
    }

    public void updateUser(UserDto.Update userDto) {
        this.username = userDto.getUsername();
        this.nickname = userDto.getNickname();
        this.birth = userDto.getBirth();
    }
}
