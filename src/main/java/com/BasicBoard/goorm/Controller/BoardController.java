package com.BasicBoard.goorm.Controller;

import com.BasicBoard.goorm.application.dto.BoardDto;
import com.BasicBoard.goorm.application.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/newpost")
    public ResponseEntity<?> newPost(@RequestBody BoardDto.Create boardDto) {
        BoardDto.Create dto = boardService.createBoard(boardDto);
        return ResponseEntity.ok(dto);
    }


    // 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> board(@PathVariable("boardId") long boardId) {
        // 엔티티 하나만 있는데도 dto에 담은 이유:
        // 나중에 다른 파라미터를 받아서 확장할 때 쉽게 하기위함..
        BoardDto.ReadRequest readRequest = BoardDto.ReadRequest.toDto(boardId);

        BoardDto.ReadResponse readResponse = boardService.readBoard(readRequest);
        return ResponseEntity.ok(readResponse);
    }


    // 게시글 전체 조회
    @GetMapping("/boards")
    public ResponseEntity<?> boardLists(Pageable pageable) {
        Page<BoardDto.ReadResponse> responseDto = boardService.readBoardLists(pageable);
        return ResponseEntity.ok(responseDto.getContent());
    }

    // 게시글 검색
    @GetMapping("/searchBoardList")
    public ResponseEntity<?> searchBoardList(@RequestBody BoardDto.Search boardDto, Pageable pageable) {
        Page<BoardDto.ReadResponse> responseDto = boardService.findArticleList(boardDto, pageable);
        return ResponseEntity.ok(responseDto.getContent());

    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}/delete")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardId") long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(boardId);
    }
}
