package com.basic_board.controller;

import com.basic_board.dto.UserDto;
import com.basic_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 사용자 생성
    @PostMapping
    public ResponseEntity<UserDto.Response> createUser(@RequestBody UserDto.Request userDto) {
        UserDto.Response response = userService.save(userDto); // 서비스에서 DTO를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사용자 조회 (ID로 조회)
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getUserById(@PathVariable("id") Long id) {
        UserDto.Response response = userService.getUserById(id); // 서비스에서 DTO를 반환
        return ResponseEntity.ok(response);
    }

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<Page<UserDto.Response>> getAllUsers(Pageable pageable) {
        Page<UserDto.Response> response = userService.getAllUsers(pageable);
        return ResponseEntity.ok(response);
    }

    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserDto.Response> updateUser(@PathVariable("id") Long id, @RequestBody UserDto.Request userDto) {
        UserDto.Response response = userService.updateUser(id, userDto); // 서비스에서 DTO를 반환
        return ResponseEntity.ok(response);
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
