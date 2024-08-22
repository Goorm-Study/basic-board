package com.basic_board.service;

import com.basic_board.dto.UserDto;
import com.basic_board.entity.User;
import com.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // 사용자 생성
    public User save(UserDto.Request userDto) {
        return userRepository.save(userDto.toEntity());
    }

    // 사용자 조회 (ID로 조회)
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // 모든 사용자 조회
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll(); // List<User> 반환
    }

    // 사용자 수정
    public User updateUser(Long id, UserDto.Request userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        user.updateUser(userDto.getUsername(), userDto.getNickname(), userDto.getBirth());
        return userRepository.save(user);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        userRepository.delete(user);
    }
}
