package com.basic_board.controller;

import com.basic_board.dto.CommentDto;
import com.basic_board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentDto.Response> createComment(@RequestBody CommentDto.Request request) {
        CommentDto.Response response = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 댓글 조회 (ID로 조회)
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto.Response> getCommentById(@PathVariable("id") Long id) {
        CommentDto.Response response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

    // 모든 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentDto.Response>> getAllComments() {
        List<CommentDto.Response> responses = commentService.getAllComments();
        return ResponseEntity.ok(responses);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto.Response> updateComment(@PathVariable("id") Long id, @RequestBody CommentDto.Request request) {
        CommentDto.Response response = commentService.updateComment(id, request);
        return ResponseEntity.ok(response);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
