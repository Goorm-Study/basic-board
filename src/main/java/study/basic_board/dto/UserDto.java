package study.basic_board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private LocalDate birth;
    private String nickname;
}
