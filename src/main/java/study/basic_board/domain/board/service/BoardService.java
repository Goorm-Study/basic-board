package study.basic_board.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.basic_board.domain.board.dto.BoardCreateRequestDto;
import study.basic_board.domain.board.dto.BoardUpdateRequestDto;
import study.basic_board.domain.board.dto.BoardResponseDto;
import study.basic_board.domain.board.entity.Board;
import study.basic_board.domain.user.entity.User;
import study.basic_board.domain.board.repository.BoardRepository;
import study.basic_board.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 글 등록
    public BoardResponseDto registerBoard(Long boardId, BoardCreateRequestDto boardCreateRequestDto) {
        Long userId = boardCreateRequestDto.getUserId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        Board board = new Board(user, boardCreateRequestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }


    // 글 전체 검색
    // repository에서 쿼리 작성하거나, 페이징 기술 사용할 것!!
    // 다른 메서드들도 체크,
    // 그리고 max도 설정할 것! List 초과 방지
    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAllBoards() {
        List<Board> BoardList = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        // 이런거 Repository에서 dto로 데이터 형식 변경할 수 있음.
        for (Board board : BoardList) {
            boardResponseDtoList.add(new BoardResponseDto(board));
        }

        return boardResponseDtoList;
    }

    // 글 제목으로 검색
    @Transactional(readOnly = true)
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
    public Long updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Long userId = boardUpdateRequestDto.getUserId();

        // 글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        // 권한을 이렇게 체크?
        if (user.getUsername().equals(board.getUser().getUsername())) {
            board.update(boardUpdateRequestDto);
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
