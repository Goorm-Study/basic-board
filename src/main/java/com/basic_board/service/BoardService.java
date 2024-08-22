package com.basic_board.service;

import com.basic_board.dto.BoardDto;
import com.basic_board.entity.Board;
import com.basic_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //게시판 글 추가
    public Board save(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity());
    }

//    public Board getBoardById(Long id) {
//        return boardRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Board not found with id " + id));
//    }
//
//    public List<Board> getAllBoards() {
//        return boardRepository.findAll();
//    }
//
//    public Board updateBoard(Long id, BoardDto boardDto) {
//        Board board = getBoardById(id);
//        board.updateBoard(boardDto.getTitle(), boardDto.getContent());
//        return boardRepository.save(board);
//    }
//
//    public void deleteBoard(Long id) {
//        Board board = getBoardById(id);
//        boardRepository.delete(board);
//    }
}
