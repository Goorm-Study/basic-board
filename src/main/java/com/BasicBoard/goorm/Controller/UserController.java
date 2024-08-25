package com.BasicBoard.goorm.Controller;

import com.BasicBoard.goorm.application.dto.UserDto;
import com.BasicBoard.goorm.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 유저등록
    @PostMapping("/register")
    // response entity 만들어서 사용하기
    public ResponseEntity<?> register(@RequestBody UserDto.Register userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok(userDto);
    }


    // 유저 업데이트
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto.Update userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.ok(userDto);
    }


    // 모든 유저 조회
    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers(Pageable pageable) {
        Page<UserDto.Read> allUsers = userService.findAllUsers(pageable);
        return ResponseEntity.ok(allUsers.getContent());
    }
}
