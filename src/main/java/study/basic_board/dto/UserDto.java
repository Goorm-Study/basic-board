package study.basic_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import study.basic_board.entity.User;

import java.time.LocalDate;

@Builder
@Getter
// setter를 사용하면 안되는 이유 : 변경 포인트가 너무 많아져서 유지보수가 어려워짐
public class UserDto {

    private Long id;

    @NotBlank(message = "사용자 이름은 필수입니다.")
    private String username;

    @NotNull(message = "생년월일은 필수입니다.")
    private LocalDate birth;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .birth(user.getBirth())
                .nickname(user.getNickname())
                .build();
    }
}
