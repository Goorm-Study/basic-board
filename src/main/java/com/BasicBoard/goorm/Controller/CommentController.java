package com.BasicBoard.goorm.Controller;

import com.BasicBoard.goorm.application.dto.CommentDto;
import com.BasicBoard.goorm.application.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 달기
    @PostMapping("/comment/add")
    public ResponseEntity<?> addComment(@RequestBody CommentDto.Create commentDto) {
        CommentDto.Create savedComment = commentService.saveComment(commentDto);
        return ResponseEntity.ok(savedComment);
    }

}
