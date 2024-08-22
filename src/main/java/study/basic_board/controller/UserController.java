package study.basic_board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.basic_board.dto.UserDto;
import study.basic_board.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 등록
    @PostMapping("/users")
    public UserDto register(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return userDto; // 성공적으로 처리되었는지, 그리고 어떤 데이터가 저장되었는지를 확인시켜주기 위해
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    // 이름으로 유저 검색
    @GetMapping("/users/{username}")
    public List<UserDto> findUsersByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    // 유저 정보 수정
    @PutMapping("/users/{id}")
    public Long updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    // 유저 삭제
    @DeleteMapping("/users/{id}")
    public Long deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
