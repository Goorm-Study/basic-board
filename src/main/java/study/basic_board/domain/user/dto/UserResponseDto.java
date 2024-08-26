package study.basic_board.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDto {

    private String username;
    private LocalDate birth;
    private String nickname;
}
