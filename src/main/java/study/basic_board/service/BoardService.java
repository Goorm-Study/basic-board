package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.basic_board.dto.BoardRequestDto;
import study.basic_board.entity.Board;
import study.basic_board.entity.User;
import study.basic_board.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    // 글 등록
    public void registerBoard(User user, BoardRequestDto boardDto) {
        Board board = new Board(user, boardDto);
        boardRepository.save(board);
    }


    // 글 전체 검색
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    // 글 제목으로 검색
    public List<Board> findBoardsByTitle(String title) {
        return boardRepository.findBoardsByTitleContaining(title);
    }


    // 글 수정
    public void updateBoard(User user, Long id, BoardRequestDto boardRequestDto) {
        // 글 찾기
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if (user.getUsername().equals(board.getUser().getUsername())) {
            board.update(boardRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
    }


    // 글 삭제
    public void deleteBoard(User user, Long id) {
        // 글 찾기
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if (user.getUsername().equals(board.getUser().getUsername())) {
            boardRepository.delete(board);
        } else {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
    }
}
