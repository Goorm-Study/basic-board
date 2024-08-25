package com.BasicBoard.goorm.entity;

import com.BasicBoard.goorm.application.dto.BoardDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    Collection<Comment> comments;

    @Builder
    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updateBoard(BoardDto.Update boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }
}
