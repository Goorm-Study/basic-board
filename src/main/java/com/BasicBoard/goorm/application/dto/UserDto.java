package com.BasicBoard.goorm.application.dto;

import com.BasicBoard.goorm.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserDto {

    @Getter
    @Builder
    public static class Read {
        private String username;
        private String nickname;
        private LocalDate birth;

        public static Read toDto(User user) {
            Read dto = Read.builder()
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .birth(user.getBirth())
                    .build();
            return dto;
        }
    }

    @Getter
    public static class Register {
        private String username;
        private String nickname;
        private LocalDate birth;
    }

    @Getter
    public static class Update {
        private Long userId;
        private String username;
        private String nickname;
        private LocalDate birth;
    }

    @Getter
    public static class Delete {
        private Long userId;
    }
}
