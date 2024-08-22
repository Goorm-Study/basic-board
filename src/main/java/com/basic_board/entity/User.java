package com.basic_board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false) // 4
    private LocalDate birth;

    @Column(nullable = false) // 4
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

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
