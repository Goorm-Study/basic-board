package com.basic_board.service;

import com.basic_board.dto.UserDto;
import com.basic_board.entity.User;
import com.basic_board.exception.UserNotFoundException;
import com.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // 사용자 생성
    public UserDto.Response save(UserDto.Request userDto) {
        User user = userDto.toEntity();
        User savedUser = userRepository.save(user);
        return UserDto.Response.fromEntity(savedUser);
    }

    // 사용자 조회 (ID로 조회)
    @Transactional(readOnly = true)
    public UserDto.Response getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserDto.Response.fromEntity(user);
    }

    // 모든 사용자 조회
    @Transactional(readOnly = true)
    public Page<UserDto.Response> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDto.Response::fromEntity);  // 엔티티 -> DTO로 변환
    }

    // 사용자 수정
    public UserDto.Response updateUser(Long id, UserDto.Request userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.updateUser(userDto.getUsername(), userDto.getNickname(), userDto.getBirth());
        User updatedUser = userRepository.save(user);
        return UserDto.Response.fromEntity(updatedUser);
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }
}
