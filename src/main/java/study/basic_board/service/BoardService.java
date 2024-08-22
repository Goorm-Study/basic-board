package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.basic_board.dto.BoardRequestDto;
import study.basic_board.dto.BoardResponseDto;
import study.basic_board.entity.Board;
import study.basic_board.entity.User;
import study.basic_board.repository.BoardRepository;
import study.basic_board.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 글 등록
    public BoardResponseDto registerBoard(Long boardId, BoardRequestDto BoardRequestDto) {
        Long userId = BoardRequestDto.getUserId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        Board board = new Board(user, BoardRequestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }


    // 글 전체 검색
    public List<BoardResponseDto> findAllBoards() {
        List<Board> BoardList = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : BoardList) {
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
    // 권한은 어떻게 체크?
    public Long updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Long userId = boardRequestDto.getUserId();

        // 글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        // 권한을 이렇게 체크?
        if (user.getUsername().equals(board.getUser().getUsername())) {
            board.update(boardRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        return board.getId();
    }


    // 글 삭제
    // 권한은 어떻게 체크?
    public Long deleteBoard(Long boardId) {
        // 글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        boardRepository.delete(board);

        return board.getId();
    }
}
