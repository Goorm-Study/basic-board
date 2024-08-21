package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.basic_board.dto.BoardRequestDto;
import study.basic_board.dto.BoardResponseDto;
import study.basic_board.entity.Board;
import study.basic_board.entity.User;
import study.basic_board.repository.BoardRepository;

import java.util.ArrayList;
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
    public List<BoardResponseDto> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : boards) {
            boardResponseDtoList.add(new BoardResponseDto(board));
        }

        return boardResponseDtoList;
    }

    // 글 제목으로 검색
    public List<BoardResponseDto> findBoardsByTitle(String title) {
        List<Board> BoardList = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        for (Board board : BoardList) {
            if (board.getTitle().contains(title)) {
                boardResponseDtoList.add(new BoardResponseDto(board));
            }
        }

        return boardResponseDtoList;
        // BoardResponseDto를 통해서 전달
    }


    // 글 수정 : 현재 접속 유저 & 글 id & dto 정보 이용
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
