package com.basic_board.integration;

import com.basic_board.dto.UserDto;
import com.basic_board.entity.User;
import com.basic_board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 생성 테스트")
    void createUserTest() throws Exception {
        // given
        UserDto.Request requestDto = new UserDto.Request("john_doe", "John", LocalDate.of(1990, 1, 1));
        String requestBody = objectMapper.writeValueAsString(requestDto);

        // when, then
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.nickname").value("John"));
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    void getUserByIdTest() throws Exception {
        // given
        User savedUser = userRepository.save(new User("john_doe", "John", LocalDate.of(1990, 1, 1)));

        // when, then
        mvc.perform(get("/users/{id}", savedUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.nickname").value("John"));
    }

    @Test
    @DisplayName("사용자 수정 테스트")
    void updateUserTest() throws Exception {
        // given
        User savedUser = userRepository.save(new User("john_doe", "John", LocalDate.of(1990, 1, 1)));
        UserDto.Request requestDto = new UserDto.Request("jane_doe", "Jane", LocalDate.of(1995, 5, 5));
        String requestBody = objectMapper.writeValueAsString(requestDto);

        // when, then
        mvc.perform(put("/users/{id}", savedUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jane_doe"))
                .andExpect(jsonPath("$.nickname").value("Jane"));
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void deleteUserTest() throws Exception {
        // given
        User savedUser = userRepository.save(new User("john_doe", "John", LocalDate.of(1990, 1, 1)));

        // when, then
        mvc.perform(delete("/users/{id}", savedUser.getUserId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("모든 사용자 페이징 조회 테스트")
    void getAllUsersWithPagingTest() throws Exception {
        // given
        userRepository.save(new User("john_doe", "John", LocalDate.of(1990, 1, 1)));
        userRepository.save(new User("jane_doe", "Jane", LocalDate.of(1995, 5, 5)));

        // when, then
        mvc.perform(get("/users")
                        .param("page", "0")  // 페이지 번호
                        .param("size", "10") // 페이지 크기
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].username").value("john_doe")) // 첫 번째 사용자
                .andExpect(jsonPath("$.content[1].username").value("jane_doe")) // 두 번째 사용자
                .andExpect(jsonPath("$.totalElements").value(2))  // 총 사용자 수 확인
                .andExpect(jsonPath("$.totalPages").value(1));  // 총 페이지 수 확인
    }
}