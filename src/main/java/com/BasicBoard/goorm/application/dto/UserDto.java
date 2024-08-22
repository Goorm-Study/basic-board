package com.BasicBoard.goorm.application.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserDto {

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
