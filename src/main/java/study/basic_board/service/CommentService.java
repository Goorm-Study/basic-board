package study.basic_board.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.basic_board.dto.CommentRequestDto;
import study.basic_board.dto.CommentResponseDto;
import study.basic_board.entity.Board;
import study.basic_board.entity.Comment;
import study.basic_board.entity.User;
import study.basic_board.repository.CommentRepository;

import java.util.ArrayList;
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
    public List<CommentResponseDto> findCommentsByBoardId(Long boardId) {
        List<Comment> CommentList = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : CommentList) {
            if (comment.getBoard().getId().equals(boardId)) {
                commentResponseDtoList.add(new CommentResponseDto(comment));
            }
        }

        return commentResponseDtoList;
        // CommentResponseDto를 통해서 전달해야함
    }

    // 댓글 수정
    public void updateBoard(User user, CommentRequestDto commentRequestDto) {
        // 댓글 찾기
        Comment comment = commentRepository.findById(commentRequestDto.getCommentId()).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        if (user.equals(comment.getUser())) {
            comment.update(commentRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
    }

    // 댓글 삭제
    public void deleteComment(User user, Long commentId) {
        // repo에서 댓글 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        if (user.equals(comment.getUser())) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
    }
}
