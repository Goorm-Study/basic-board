package com.BasicBoard.goorm.application.service;

import com.BasicBoard.goorm.application.dto.UserDto;
import com.BasicBoard.goorm.entity.User;
import com.BasicBoard.goorm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // register user
    @Transactional
    public void registerUser(UserDto.Register userDto) {

        // nickname duplicate check
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new IllegalArgumentException("nickname already exists. nickname should be unique");
        }

        try {
            User user = User.builder()
                    .username(userDto.getUsername())
                    .nickname(userDto.getNickname())
                    .birth(userDto.getBirth())
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("nickname already exists", e);
        }
    }

    // findAllUsers
    @Transactional(readOnly = true)
    public Page<UserDto.Read> findAllUsers(Pageable pageable) {
        Page<UserDto.Read> userList = userRepository.findAll(pageable).map(UserDto.Read::toDto);
        return userList;
    }

    // findById
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("non exits"));
    }

    // findByNickName
    @Transactional(readOnly = true)
    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(() -> new NoSuchElementException("non exists"));
    }

    // updateUser

    public void updateUser(UserDto.Update updateDto) {
        User user = userRepository.findById(updateDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("회원정보 없음"));

        try {
            user.updateUser(updateDto);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("nickname already exists", e);

        }


    }

    public void deleteUser(UserDto.Delete deleteDto) {
        userRepository.deleteById(deleteDto.getUserId());
    }

}
