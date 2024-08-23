package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.basic_board.dto.board.BoardCreateRequestDto;
import study.basic_board.dto.board.BoardUpdateRequestDto;
import study.basic_board.dto.board.BoardResponseDto;
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
    @Transactional
    public BoardResponseDto registerBoard(BoardCreateRequestDto boardCreateRequestDto) {
        Long userId = boardCreateRequestDto.getUserId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        Board board = Board.builder()
                .title(boardCreateRequestDto.getTitle())
                .content(boardCreateRequestDto.getContent())
                .user(user).build();

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


/*
    Update 메서드와 관련하여...
    <gpt의 의견>
    **엔티티 재생성 대신 기존 엔티티 사용**
    현재 코드에서는 boardId 기반으로 새로운 board 객체를 빌더 패턴으로 생성하고 있습니다. 그러나, 이 방법은 효율적이지 않으며, 데이터베이스의 다른 필드가 유지되지 않을 수 있는 문제가 있습니다.
    기존 엔티티를 로드한 후 그 엔티티의 필드만 업데이트하는 것이 더 적절합니다. 이렇게 하면 엔티티의 나머지 필드가 유지되고, JPA의 더 나은 변경 추적 기능을 활용할 수 있습니다.
*/

    // 글 수정 : 현재 접속 유저 & 글 id & dto 정보 이용
    // 권한은 어떻게 체크?
    @Transactional
    public Long updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {

        // 게시물이 존재하는지 체크
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. ID: " + boardId));

        // 제목은 null이면 안되니까
        if (boardUpdateRequestDto.getTitle() == null || boardUpdateRequestDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }

        board.update(boardUpdateRequestDto);

        return board.getId();
    }


    // 글 삭제
    // 권한은 어떻게 체크?
    @Transactional
    public Long deleteBoard(Long boardId) {
        // 글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        boardRepository.delete(board);

        return board.getId();
    }
}
