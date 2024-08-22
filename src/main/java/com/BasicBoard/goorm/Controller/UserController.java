package com.BasicBoard.goorm.Controller;

import com.BasicBoard.goorm.application.dto.UserDto;
import com.BasicBoard.goorm.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    // response entity 만들어서 사용하기
    public ResponseEntity<?> register(@RequestBody UserDto.Register userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto.Update userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.ok(userDto);
    }

}
