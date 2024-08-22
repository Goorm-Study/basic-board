package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.basic_board.dto.UserDto;
import study.basic_board.entity.User;
import study.basic_board.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // id 중복 확인
    // 유저 등록 구현
    public UserDto registerUser(UserDto userDto) {
        // 닉네임 중복되면 에러
        if (!userRepository.existsByNickname(userDto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        User user = new User(userDto);
        userRepository.save(user);
        return userDto;
    }


    // 유저 검색 기능
    // 1. 전체 검색 기능
    // @Transactional(readOnly = true) -> 트랜잭션 이해하고 사용하자
    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            userDtoList.add(new UserDto(user));
        }

        return userDtoList;
    }

    // 2. 이름으로 검색 기능
    // @Transactional(readOnly = true) -> 트랜잭션 이해하고 사용하자
    public List<UserDto> findByUsername(String username) {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        // 질문 : 굳이 이렇게 하나하나 dto로 전환해야하나?
        for (User user : userList) {
            if (user.getUsername().contains(username)) {
                userDtoList.add(new UserDto(user));
            }
        }

        return userDtoList;
    }


    // 유저 정보 수정 구현
    public Long updateUser(UserDto userDto) {
        Long id = userDto.getId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. ID: " + id));

        user.updateUser(userDto);
        userRepository.save(user);

        return id;
    }


    // 유저 삭제 기능
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
