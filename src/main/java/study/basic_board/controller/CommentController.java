package study.basic_board.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.basic_board.dto.CommentRequestDto;
import study.basic_board.dto.CommentResponseDto;
import study.basic_board.service.CommentService;

import java.util.List;

@RestController
@NoArgsConstructor
public class CommentController {
    private CommentService commentService;

    // 댓글 등록
    @PostMapping("/boards/{id}/comments")
    public CommentResponseDto registerComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.registerComment(id, commentRequestDto);
    }

    // 댓글 조회
    @GetMapping("/boards/{id}/comments")
    public List<CommentResponseDto> findCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.findCommentsByBoardId(boardId);
    }

    // 댓글 수정
    // 약간 비효율 : commentId가 중복됨.
    @PutMapping("/boards/{id}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentId, commentRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/boards/{id}/comments/{commentid}")
    public Long deleteComment(@PathVariable Long commentid) {
        return commentService.deleteComment(commentid);
    }
}
