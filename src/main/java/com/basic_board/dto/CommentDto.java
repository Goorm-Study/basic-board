package com.basic_board.dto;

import com.basic_board.entity.Board;
import com.basic_board.entity.Comment;
import com.basic_board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

    // 클라이언트에서 댓글을 생성하거나 수정할 때 사용하는 Request DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String content;
        private Long boardId;
        private Long userId;

        public Comment toEntity(Board board, User user) {
            return Comment.builder()
                    .content(content)
                    .board(board)
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
        private Long commentId;
        private String content;
        private Long boardId;
        private Long userId;

        public static Response fromEntity(Comment comment) {
            return Response.builder()
                    .commentId(comment.getCommentId())
                    .content(comment.getContent())
                    .boardId(comment.getBoard().getBoardId())
                    .userId(comment.getUser().getUserId())
                    .build();
        }
    }
}
