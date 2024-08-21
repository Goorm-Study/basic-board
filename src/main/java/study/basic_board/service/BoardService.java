package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.basic_board.dto.BoardDto;
import study.basic_board.entity.Board;
import study.basic_board.entity.User;
import study.basic_board.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 글 등록
    public void registerBoard(User user, BoardDto boardDto) {
        Board board = new Board(user, boardDto);
        boardRepository.save(board);
    }

    // 글 전체 검색
    public List<Board>
}
