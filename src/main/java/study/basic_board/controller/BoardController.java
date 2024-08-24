package study.basic_board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import study.basic_board.dto.board.BoardCreateRequestDto;
import study.basic_board.dto.board.BoardUpdateRequestDto;
import study.basic_board.dto.board.BoardResponseDto;
import study.basic_board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 글 등록
    @PostMapping("/boards")
    public BoardResponseDto registerBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        BoardResponseDto dto = boardService.registerBoard(boardCreateRequestDto);
        return dto;
    }

    // 글 전체 검색
    @GetMapping("/boards")
    // PageRequest를 컨트롤러에서 전달받는 것이 좋다.
    // 다른 정렬 조건을 붙이고 싶으면 서비스에 메서드를 새로 추가해야되는 구조이기 때문이다.
    public List<BoardResponseDto> findAllBoards(final Pageable pageable) {
        return boardService.findAllBoards(pageable);
    }

    // 글 제목으로 검색
    @GetMapping("/boards/{title}")
    public List<BoardResponseDto> findBoardsByTitle(@PathVariable String title, final Pageable pageable) {
        return boardService.findBoardsByTitle(title,pageable);
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
