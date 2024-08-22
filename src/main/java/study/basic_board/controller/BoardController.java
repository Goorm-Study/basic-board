package study.basic_board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.basic_board.dto.board.BoardUpdateRequestDto;
import study.basic_board.dto.board.BoardResponseDto;
import study.basic_board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 글 등록
    @PostMapping("/boards/{id}")
    public BoardResponseDto registerBoard(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        BoardResponseDto dto = boardService.registerBoard(id, boardUpdateRequestDto);
        return dto;
    }

    // 글 전체 검색
    @GetMapping("/boards")
    public List<BoardResponseDto> getAllBoards() {
        return boardService.findAllBoards();
    }

    // 글 제목으로 검색
    @GetMapping("/boards/{title}")
    public List<BoardResponseDto> getBoardsByTitle(@PathVariable String title) {
        return boardService.findBoardsByTitle(title);
    }

    // 글 수정
    @PutMapping("/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        return boardService.updateBoard(id, boardUpdateRequestDto);
    }

    // 글 삭제
    @DeleteMapping("/boards/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }
}
