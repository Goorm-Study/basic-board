package com.BasicBoard.goorm.application.dto;

import com.BasicBoard.goorm.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDto {

    @Getter
    public static class Create {
        private String title;
        private String content;
        private Long userId;
    }

    @Getter
    @Builder
    public static class ReadResponse {
        private Long boardId;
        private String nickname;
        private String title;
        private String content;
        private Long commentCount;


        public static ReadResponse toDto(Board board) {
            return ReadResponse.builder()
                    .boardId(board.getBoardId())
                    .nickname(board.getUser().getNickname())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ReadRequest {
        private Long boardId;

        public static ReadRequest toDto(Long boardId) {
            return ReadRequest.builder()
                    .boardId(boardId)
                    .build();
        }
    }

    @Getter
    public static class Search {
        private String query;

        // 1: 제목으로 검색
        // 2: 닉네임으로 검색
        private int searchBy;
    }

}
