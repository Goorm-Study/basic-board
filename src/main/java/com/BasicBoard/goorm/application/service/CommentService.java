package com.BasicBoard.goorm.application.service;


import com.BasicBoard.goorm.application.dto.CommentDto;
import com.BasicBoard.goorm.entity.Board;
import com.BasicBoard.goorm.entity.Comment;
import com.BasicBoard.goorm.entity.User;
import com.BasicBoard.goorm.repository.BoardRepository;
import com.BasicBoard.goorm.repository.CommentRepository;
import com.BasicBoard.goorm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // comment dto to entity & save
    public CommentDto.Create saveComment(CommentDto.Create commentDto) {

        // 현재 로그인 기능이 없어 인가 안됨 -> 유저아이디만 알고있으면 아무나 해당 유저인것처럼 사용가능
        Long userId = commentDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

        Long boardId = commentDto.getBoardId();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));

        Comment comment = Comment.builder()
                .user(user)
                .board(board)
                .content(commentDto.getContent()).build();

        commentRepository.save(comment);

        return commentDto;
    }


}
