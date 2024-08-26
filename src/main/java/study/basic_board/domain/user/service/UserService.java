package study.basic_board.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.basic_board.domain.board.dto.BoardResponseDto;
import study.basic_board.domain.board.repository.BoardRepository;
import study.basic_board.domain.user.dto.UserListResponseDto;
import study.basic_board.domain.user.dto.UserRequestDto;
import study.basic_board.domain.user.dto.UserResponseDto;
import study.basic_board.domain.user.entity.User;
import study.basic_board.domain.user.repository.UserRepository;
import study.basic_board.global.exception.ApiException;
import study.basic_board.global.exception.ErrorType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final int TOTAL_ITEMS_PER_PAGE = 10;

    // id 중복 확인
    // 유저 등록 구현
    @Transactional
    public String registerUser(UserRequestDto userDto) {
        // 닉네임 중복되면 에러
        if (!userRepository.existsByNickname(userDto.getNickname())) {
            throw new ApiException(ErrorType._DUPLICATE_NICKNAME);
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .birth(userDto.getBirth())
                .nickname(userDto.getNickname())
                .build();

        userRepository.save(user);

        return "Registered successfully";
    }


    // 유저 검색 기능
    // 1. 전체 검색 기능
    public UserListResponseDto findAllUsers(int page) {
        List<User> userList = userRepository.findAll();
        Page<User> userPage = userRepository.findAll(
                PageRequest.of(page, TOTAL_ITEMS_PER_PAGE)
        );

        return UserListResponseDto.builder()
                .totalPages(userPage.getTotalPages())
                .currentPage(userPage.getNumber())
                .responseDtoList(userPage.getContent().stream().map((user) -> UserResponseDto.builder()
                        .username(user.getUsername())
                        .birth(user.getBirth())
                        .nickname(user.getNickname())
                        .build()).collect(Collectors.toList())).build();
    }

    // 2. 이름으로 검색 기능
    @Transactional(readOnly = true) // -> 트랜잭션 이해하고 사용하자
    public UserListResponseDto findByUsername(int page, String username) {
        List<User> userList = userRepository.findAll();
        Page<User> userPage = userRepository.findAllByUsername(
                PageRequest.of(page, TOTAL_ITEMS_PER_PAGE),
                username
        );

        return UserListResponseDto.builder()
                .totalPages(userPage.getTotalPages())
                .currentPage(userPage.getNumber())
                .responseDtoList(userPage.getContent().stream().map((user) -> UserResponseDto.builder()
                        .username(user.getUsername())
                        .birth(user.getBirth())
                        .nickname(user.getNickname())
                        .build()).collect(Collectors.toList())).build();
    }


    // 유저 정보 수정 구현
    public Long updateUser(UserRequestDto userDto) {
        Long id = userDto.getId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. ID: " + id));

        user.updateUser(userDto.getUsername(), userDto.getNickname(), userDto.getBirth());
        userRepository.save(user);

        return id;
    }


    // 유저 삭제 기능
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
