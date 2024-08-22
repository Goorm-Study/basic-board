package com.basic_board.service;

import com.basic_board.dto.BoardDto;
import com.basic_board.entity.Board;
import com.basic_board.entity.User;
import com.basic_board.repository.BoardRepository;
import com.basic_board.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    public BoardDto.Response createBoard(BoardDto.Request request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + request.getUserId()));

        Board board = boardRepository.save(request.toEntity(user));
        return BoardDto.Response.fromEntity(board);
    }

    // 게시글 조회 (ID로 조회)
    @Transactional(readOnly = true)
    public BoardDto.Response getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found with id " + id));
        return BoardDto.Response.fromEntity(board);
    }

    // 모든 게시글 조회
    @Transactional(readOnly = true)
    public List<BoardDto.Response> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(BoardDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 게시글 수정
    public BoardDto.Response updateBoard(Long id, BoardDto.Request request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found with id " + id));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + request.getUserId()));

        board.updateBoard(request.getTitle(), request.getContent());
        return BoardDto.Response.fromEntity(boardRepository.save(board));
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found with id " + id));
        boardRepository.delete(board);
    }
}
