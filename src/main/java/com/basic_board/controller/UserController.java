package com.basic_board.controller;

import com.basic_board.dto.UserDto;
import com.basic_board.entity.User;
import com.basic_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 사용자 생성
    @PostMapping
    public ResponseEntity<UserDto.Response> createUser(@RequestBody UserDto.Request userDto) {
        User savedUser = userService.save(userDto); // 엔티티 반환
        UserDto.Response response = UserDto.Response.fromEntity(savedUser); // 엔티티 -> DTO로 변환
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사용자 조회 (ID로 조회)
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id); // User 엔티티 반환
        UserDto.Response response = UserDto.Response.fromEntity(user); // 엔티티 -> DTO 변환
        return ResponseEntity.ok(response);
    }

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<List<UserDto.Response>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // 엔티티 목록 반환
        List<UserDto.Response> response = users.stream()
                .map(UserDto.Response::fromEntity) // 엔티티 -> DTO로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserDto.Response> updateUser(@PathVariable("id") Long id, @RequestBody UserDto.Request userDto) {
        User updatedUser = userService.updateUser(id, userDto); // 엔티티 반환
        UserDto.Response response = UserDto.Response.fromEntity(updatedUser); // 엔티티 -> DTO로 변환
        return ResponseEntity.ok(response);
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
