package study.basic_board.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.basic_board.dto.comment.CommentCreateRequestDto;
import study.basic_board.dto.comment.CommentResponseDto;
import study.basic_board.dto.comment.CommentUpdateRequestDto;
import study.basic_board.entity.Board;
import study.basic_board.entity.Comment;
import study.basic_board.entity.User;
import study.basic_board.repository.BoardRepository;
import study.basic_board.repository.CommentRepository;
import study.basic_board.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 댓글 등록
    @Transactional
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
    public List<CommentResponseDto> findCommentsByBoardId(Long boardId, Pageable pageable) {
        // 글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<Comment> CommentList = board.getComments();

        // 댓글 기능의 경우, 먼저 게시글의 id로 게시글을 찾아서, 그 게시글에 들어있는 댓글을
        // 확인하는 구조이기 때문에, 이렇게 수동적으로 구현했습니다.
        // repository에서 수많은 댓글들을 검색하게되는 것은 너무 비효율적이라고 생각했습니다.
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), CommentList.size());
        List<Comment> paginatedList = CommentList.subList(start, end);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : paginatedList) {
                commentResponseDtoList.add(new CommentResponseDto(comment));
        }

        return commentResponseDtoList;
        // CommentResponseDto를 통해서 전달해야함
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        // 댓글 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        comment.update(commentUpdateRequestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public Long deleteComment(Long commentId) {
        // repo에서 댓글 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        commentRepository.delete(comment);

        return comment.getId();
    }
}
