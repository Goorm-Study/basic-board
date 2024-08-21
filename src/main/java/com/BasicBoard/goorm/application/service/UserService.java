package com.BasicBoard.goorm.application.service;

import com.BasicBoard.goorm.application.dto.UserDto;
import com.BasicBoard.goorm.entity.User;
import com.BasicBoard.goorm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // register user
    public void RegisterUser(UserDto userDto) {
        // nickname duplicate check
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new IllegalArgumentException("nickname already exists. nickname should be unique");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .birth(userDto.getBirth())
                .build();

        userRepository.save(user);
    }

    // findAllUsers
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // findById
    public User findById(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("non exits"));
    }

    // findByNickName
    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(() -> new NoSuchElementException("non exists"));
    }

    // updateUser
    // 닉네임을 변경?
    public void updateUser(UserDto dto) {
    }

}
