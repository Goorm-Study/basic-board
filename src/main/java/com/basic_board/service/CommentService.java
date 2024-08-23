package com.basic_board.service;

import com.basic_board.dto.CommentDto;
import com.basic_board.entity.Board;
import com.basic_board.entity.Comment;
import com.basic_board.entity.User;
import com.basic_board.repository.BoardRepository;
import com.basic_board.repository.CommentRepository;
import com.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentDto.Response createComment(CommentDto.Request request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found with id " + request.getBoardId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + request.getUserId()));

        Comment comment = request.toEntity(board, user);
        return CommentDto.Response.fromEntity(commentRepository.save(comment));
    }

    // 댓글 조회 (ID로 조회)
    @Transactional(readOnly = true)
    public CommentDto.Response getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
        return CommentDto.Response.fromEntity(comment);
    }

    // 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDto.Response> getAllComments() {
        return commentRepository.findAll().stream()
                .map(CommentDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 댓글 수정
    public CommentDto.Response updateComment(Long id, CommentDto.Request request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));

        comment.updateContent(request.getContent());
        return CommentDto.Response.fromEntity(commentRepository.save(comment));
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
        commentRepository.delete(comment);
    }
}
