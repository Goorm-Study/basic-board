package study.basic_board.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.basic_board.domain.user.dto.UserListResponseDto;
import study.basic_board.domain.user.dto.UserRequestDto;
import study.basic_board.domain.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 등록
    @PostMapping("/users")
    public String register(@RequestBody UserRequestDto userDto) {
        return userService.registerUser(userDto);
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public UserListResponseDto findAllUsers(@RequestParam(required = false, defaultValue = "0", value = "page") int page
                                            ) {
        return userService.findAllUsers(page);
    }

    // 이름으로 유저 검색
    @GetMapping("/users/{username}")
    public UserListResponseDto findUsersByUsername(@RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                                    @RequestParam String username) {
        return userService.findByUsername(page, username);
    }

    // 유저 정보 수정
    @PutMapping("/users/{id}")
    public Long updateUser(@RequestBody UserRequestDto userDto) {
        return userService.updateUser(userDto);
    }

    // 유저 삭제
    @DeleteMapping("/users/{id}")
    public Long deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
