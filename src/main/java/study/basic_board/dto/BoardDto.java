package study.basic_board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
// Response랑 Request 나눠야할 듯?
// response :
public class BoardDto {
    private String title;
    private String content;
}
