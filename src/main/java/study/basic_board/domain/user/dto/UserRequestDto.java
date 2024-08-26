package study.basic_board.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.basic_board.domain.user.entity.User;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
// setter를 사용하면 안되는 이유 : 변경 포인트가 너무 많아져서 유지보수가 어려워짐
public class UserRequestDto {
    private Long id;
    private String username;
    private LocalDate birth;
    private String nickname;
}
