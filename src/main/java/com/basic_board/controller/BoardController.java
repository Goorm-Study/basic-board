package com.basic_board.controller;

import com.basic_board.dto.BoardDto;
import com.basic_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<BoardDto.Response> createBoard(@RequestBody BoardDto.Request request) {
        BoardDto.Response response = boardService.createBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 조회 (ID로 조회)
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto.Response> getBoardById(@PathVariable("id") Long id) {
        BoardDto.Response response = boardService.getBoardById(id);
        return ResponseEntity.ok(response);
    }

    // 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<BoardDto.Response>> getAllBoards() {
        List<BoardDto.Response> response = boardService.getAllBoards();
        return ResponseEntity.ok(response);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto.Response> updateBoard(@PathVariable("id") Long id, @RequestBody BoardDto.Request request) {
        BoardDto.Response response = boardService.updateBoard(id, request);
        return ResponseEntity.ok(response);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
