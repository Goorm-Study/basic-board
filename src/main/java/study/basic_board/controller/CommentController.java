package study.basic_board.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.basic_board.service.CommentService;

@RestController
@NoArgsConstructor
public class CommentController {
    private CommentService commentService;

    // 댓글 등록
    @PostMapping("/boards")
    public void registerComment()
}
