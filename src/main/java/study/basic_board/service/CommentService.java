package study.basic_board.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.basic_board.dto.CommentRequestDto;
import study.basic_board.entity.Board;
import study.basic_board.entity.Comment;
import study.basic_board.entity.User;
import study.basic_board.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 등록
    public void registerComment(Board board, User user, CommentRequestDto commentRequestDto) {
        Comment comment = new Comment(board, user, commentRequestDto);
        commentRepository.save(comment);
    }

    // 댓글 읽어오기
    public List<Comment> findAll(Long boardId) {
        return commentRepository.findAllByBoardId(boardId);
        // 이게 아니라 CommentResponseDto를 통해서 전달해야함
    }

    // 댓글 수정

    // 댓글 삭제
}
