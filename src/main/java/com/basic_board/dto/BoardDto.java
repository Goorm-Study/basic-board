package com.basic_board.dto;

import com.basic_board.entity.Board;
import com.basic_board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private String title;
    private String content;
    private Long userId;  // User 객체 대신 userId

    public Board toEntity(User user) {  // User를 별도로 서비스에서 조회
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}


