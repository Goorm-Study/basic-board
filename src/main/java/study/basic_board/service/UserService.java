package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import study.basic_board.dto.UserDto;
import study.basic_board.entity.User;
import study.basic_board.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // id 중복 확인
    // 유저 등록 구현
    public UserDto registerUser(UserDto userDto) {
        // 닉네임 중복되면 에러
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        try {
            User user = new User(userDto);
            userRepository.save(user);
            return UserDto.of(user);
        } catch(DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    // 유저 검색 기능
    // 1. 전체 검색 기능
    // @Transactional(readOnly = true) -> 트랜잭션 이해하고 사용하자
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // 2. 이름으로 검색 기능
    // @Transactional(readOnly = true) -> 트랜잭션 이해하고 사용하자
    public List<User> findByUsername(String username) {
        return userRepository.findByUsernameContaining(username);
    }


    // 유저 정보 수정 구현
    public void updateUser(UserDto userDto) {
        Long id = userDto.getId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. ID: " + id));

        user.updateUser(userDto);
        userRepository.save(user);
    }


    // 유저 삭제 기능
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
