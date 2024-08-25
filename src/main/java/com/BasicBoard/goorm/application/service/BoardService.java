package com.BasicBoard.goorm.application.service;

import com.BasicBoard.goorm.application.dto.BoardDto;
import com.BasicBoard.goorm.entity.Board;
import com.BasicBoard.goorm.entity.User;
import com.BasicBoard.goorm.repository.BoardRepository;
import com.BasicBoard.goorm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardDto.Create createBoard(BoardDto.Create boardDto) {

        Long userId = boardDto.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 유저"));

        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .user(user)
                .build();

        boardRepository.save(board);
        return boardDto;
    }


    // 조회
    // dto <-> entity 서비스단에서
    @Transactional(readOnly = true)
    public BoardDto.ReadResponse readBoard(BoardDto.ReadRequest boardDto) {
        Long boardId = boardDto.getBoardId();
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 아이디"));
        return BoardDto.ReadResponse.toDto(board);
    }

    // 전체 글 목록 조회 (paging)
    @Transactional(readOnly = true)
    public Page<BoardDto.ReadResponse> readBoardLists(Pageable pageable) {
        return toDtoList(boardRepository.findAll(pageable));
    }


    // 검색어로 글 목록 조회
    // 예외처리..
    @Transactional(readOnly = true)
    public Page<BoardDto.ReadResponse> findArticleList(BoardDto.Search boardDto, Pageable pageable) {

        int searchBy = boardDto.getSearchBy();

        Page<BoardDto.ReadResponse> responseDto = switch (searchBy) {

            // 제목으로 검색
            case 1 -> toDtoList(boardRepository.findBoardsByTitleContaining(pageable, boardDto.getQuery()));

            //  닉네임으로 검색
            case 2 -> toDtoList(boardRepository.findByUser_Nickname(pageable, boardDto.getQuery()));
            default -> Page.empty();
        };


        return responseDto;
    }

    public void deleteBoard(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 아이디 입니다."));
        boardRepository.delete(board);
    }

    // Page<Board> to Page<BoardDto.ReadResponse>
    public static Page<BoardDto.ReadResponse> toDtoList(Page<Board> boardList) {
        return boardList.map(BoardDto.ReadResponse::toDto);
    }

    // update Board
    public BoardDto.Update updateBoard(BoardDto.Update boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));
        board.updateBoard(boardDto);
        boardRepository.save(board);

        return boardDto;
    }
}
