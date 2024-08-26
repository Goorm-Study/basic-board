package com.basic_board.service;

import com.basic_board.dto.CommentDto;
import com.basic_board.entity.Board;
import com.basic_board.entity.Comment;
import com.basic_board.entity.User;
import com.basic_board.exception.BoardNotFoundException;
import com.basic_board.exception.CommentNotFoundException;
import com.basic_board.exception.UserNotFoundException;
import com.basic_board.repository.BoardRepository;
import com.basic_board.repository.CommentRepository;
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
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentDto.Response createComment(CommentDto.Request request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(request.getBoardId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        Comment comment = request.toEntity(board, user);
        return CommentDto.Response.fromEntity(commentRepository.save(comment));
    }

    // 댓글 조회 (ID로 조회)
    @Transactional(readOnly = true)
    public CommentDto.Response getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        return CommentDto.Response.fromEntity(comment);
    }

    // 모든 댓글 조회
    @Transactional(readOnly = true)
    public Page<CommentDto.Response> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(CommentDto.Response::fromEntity);  // 엔티티 -> DTO로 변환
    }

    // 댓글 수정
    public CommentDto.Response updateComment(Long id, CommentDto.Request request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        comment.updateContent(request.getContent());
        return CommentDto.Response.fromEntity(commentRepository.save(comment));
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        commentRepository.delete(comment);
    }
}
