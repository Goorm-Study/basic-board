package com.BasicBoard.goorm.application.dto;

import com.BasicBoard.goorm.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {

    @Getter
    @Builder
    public static class Create {
        private Long userId;
        private Long boardId;
        private String content;
    }

    @Getter
    public static class ReadRequest {
        private Long boardId;
    }
}
