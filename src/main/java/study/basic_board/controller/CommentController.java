package study.basic_board.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import study.basic_board.dto.comment.CommentCreateRequestDto;
import study.basic_board.dto.comment.CommentResponseDto;
import study.basic_board.dto.comment.CommentUpdateRequestDto;
import study.basic_board.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/boards/{id}/comments")
    public CommentResponseDto registerComment(@PathVariable Long id, @RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        return commentService.registerComment(id, commentCreateRequestDto);
    }

    // 댓글 조회
    @GetMapping("/boards/{id}/comments")
    public List<CommentResponseDto> findCommentsByBoardId(@PathVariable Long id, final Pageable pageable) {
        return commentService.findCommentsByBoardId(id, pageable);
    }

    // 댓글 수정
    // 약간 비효율 : commentId가 중복됨.
    @PutMapping("/boards/{id}/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return commentService.updateComment(commentId, commentUpdateRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/boards/{id}/comments/{commentid}")
    public Long deleteComment(@PathVariable Long commentid) {
        return commentService.deleteComment(commentid);
    }
}
