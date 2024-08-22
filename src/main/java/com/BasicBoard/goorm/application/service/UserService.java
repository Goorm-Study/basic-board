package com.BasicBoard.goorm.application.service;

import com.BasicBoard.goorm.application.dto.UserDto;
import com.BasicBoard.goorm.entity.User;
import com.BasicBoard.goorm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // register user
    @Transactional
    public void registerUser(UserDto.Register userDto) {
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
    @Transactional
    public void updateUser(UserDto.Update updateDto) {
        User user = userRepository.findById(updateDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("회원정보 없음"));
        user.updateUser(updateDto);
    }

    @Transactional
    public void deleteUser(UserDto.Delete deleteDto) {
        userRepository.deleteById(deleteDto.getUserId());
    }

}
