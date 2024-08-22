package com.basic_board.dto;

import com.basic_board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UserDto {

    // 클라이언트에서 유저를 생성하거나 수정할 때 사용하는 Request DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String username;
        private String nickname;
        private LocalDate birth;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .nickname(nickname)
                    .birth(birth)
                    .build();
        }
    }

    // 서버에서 클라이언트로 데이터를 보낼 때 사용하는 Response DTO
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long userId;  // 유저 ID는 서버에서 생성되므로 Response에만 포함
        private String username;
        private String nickname;
        private LocalDate birth;

        // Entity에서 Response로 변환하는 메서드
        public static Response fromEntity(User user) {
            return new Response(
                    user.getUserId(),
                    user.getUsername(),
                    user.getNickname(),
                    user.getBirth()
            );
        }
    }
}
