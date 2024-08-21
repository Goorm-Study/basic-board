package study.basic_board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
// setter를 사용하면 안되는 이유 : 변경 포인트가 너무 많아져서 유지보수가 어려워짐
public class UserDto {
    private Long id;
    private String username;
    private LocalDate birth;
    private String nickname;
}
