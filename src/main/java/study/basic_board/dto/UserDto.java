package study.basic_board.dto;

import lombok.Builder;
import lombok.Getter;
import study.basic_board.entity.User;

import java.time.LocalDate;

@Builder
@Getter
// setter를 사용하면 안되는 이유 : 변경 포인트가 너무 많아져서 유지보수가 어려워짐
public class UserDto {

    private Long id;
    private String username;
    private LocalDate birth;
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
