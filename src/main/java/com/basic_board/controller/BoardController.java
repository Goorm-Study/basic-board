package com.basic_board.controller;

import com.basic_board.dto.BoardDto;
import com.basic_board.entity.Board;
import com.basic_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<Board> createBoard(BoardDto boardDto) {
        Board savedBoard = boardService.save(boardDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBoard);
    }
}
