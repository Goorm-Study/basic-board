package com.basic_board.service;

import com.basic_board.dto.BoardDto;
import com.basic_board.entity.Board;
import com.basic_board.entity.User;
import com.basic_board.exception.BoardNotFoundException;
import com.basic_board.exception.UserNotFoundException;
import com.basic_board.repository.BoardRepository;
import com.basic_board.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    @Transactional
    public BoardDto.Response createBoard(BoardDto.Request request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        Board board = boardRepository.save(request.toEntity(user));
        return BoardDto.Response.fromEntity(board);
    }

    // 게시글 조회 (ID로 조회)
    @Transactional(readOnly = true)
    public BoardDto.Response getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
        return BoardDto.Response.fromEntity(board);
    }

    // 모든 게시글 조회 (페이징 지원)
    @Transactional(readOnly = true)
    public Page<BoardDto.Response> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardDto.Response::fromEntity);
    }

    // 게시글 수정
    @Transactional
    public BoardDto.Response updateBoard(Long id, BoardDto.Request request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));

        board.updateBoard(request.getTitle(), request.getContent());
        return BoardDto.Response.fromEntity(board);
    }

    // 게시글 삭제
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id));
        boardRepository.delete(board);
    }
}
