package com.basic_board.dto;

import com.basic_board.entity.Board;
import com.basic_board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardDto {

    // 클라이언트에서 게시판 글을 생성하거나 수정할 때 사용하는 Request DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String title;
        private String content;
        private Long userId; // User 전체 객체 대신 User의 ID만 받음

        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    // 서버에서 클라이언트로 데이터를 보낼 때 사용하는 Response DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long boardId;  // 게시판 ID
        private String title;
        private String content;
        private Long userId;   // User의 ID만 반환

        public static Response fromEntity(Board board) {
            return Response.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .userId(board.getUser().getUserId())
                    .build();
        }
    }
}
