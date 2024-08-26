package study.basic_board.domain.board.comment.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.basic_board.domain.board.comment.dto.CommentCreateRequestDto;
import study.basic_board.domain.board.comment.dto.CommentResponseDto;
import study.basic_board.domain.board.comment.dto.CommentUpdateRequestDto;
import study.basic_board.domain.board.entity.Board;
import study.basic_board.domain.board.comment.entity.Comment;
import study.basic_board.domain.user.entity.User;
import study.basic_board.domain.board.repository.BoardRepository;
import study.basic_board.domain.board.comment.repository.CommentRepository;
import study.basic_board.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 댓글 등록
    public CommentResponseDto registerComment(Long boardId, CommentCreateRequestDto commentCreateRequestDto) {
        Long userId = commentCreateRequestDto.getUserId();

        // repo에서 글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        // repo에서 사용자 찾기
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );


        Comment comment = new Comment(board, user, commentCreateRequestDto);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 읽어오기
    // 여기도 트랜잭션?
    // 여기도 페이징 기술 적용하기
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentsByBoardId(Long boardId) {
        // 글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> CommentList = board.getComments();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : CommentList) {
                commentResponseDtoList.add(new CommentResponseDto(comment));
        }

        return commentResponseDtoList;
        // CommentResponseDto를 통해서 전달해야함
    }

    // 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        // 댓글 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        comment.update(commentUpdateRequestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public Long deleteComment(Long commentId) {
        // repo에서 댓글 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        commentRepository.delete(comment);

        return comment.getId();
    }
}