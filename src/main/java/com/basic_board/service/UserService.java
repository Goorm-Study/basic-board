package com.basic_board.service;

import com.basic_board.dto.UserDto;
import com.basic_board.entity.User;
import com.basic_board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 사용자 생성
    public User save(UserDto userDto) {
        return userRepository.save(userDto.toEntity());
    }

    // 사용자 조회 (ID로 조회)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 사용자 수정
    @Transactional
    public User updateUser(Long id, UserDto userDto) {
        User user = getUserById(id);
        user.updateUser(userDto.getUsername(), userDto.getNickname(), userDto.getBirth());
        return userRepository.save(user);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
