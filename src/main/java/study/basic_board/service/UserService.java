package study.basic_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.basic_board.dto.user.UserDto;
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
    @Transactional
    public UserDto registerUser(UserDto userDto) {
        // 닉네임 중복되면 에러
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .birth(userDto.getBirth())
                .nickname(userDto.getNickname())
                .build();

        userRepository.save(user);
        return userDto;
    }


    // 유저 검색 기능
    // 1. 전체 검색 기능
    @Transactional(readOnly = true) // -> 트랜잭션 이해하고 사용하자
    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            userDtoList.add(new UserDto(user));
        }

        return userDtoList;
    }

    // 2. 이름으로 검색 기능
    @Transactional(readOnly = true) // -> 트랜잭션 이해하고 사용하자
    public List<UserDto> findByUsername(String username) {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        // 질문 : 굳이 이렇게 하나하나 dto로 전환해야하나?
        // 답 : repo에서 JPQL 구현하는게 맞음. 이렇게 하면 유지보수가 어려워지기 때문
        for (User user : userList) {
            if (user.getUsername().contains(username)) {
                userDtoList.add(new UserDto(user));
            }
        }

        return userDtoList;
    }

/*
    Update 메서드와 관련하여...
    <gpt의 의견>
    **엔티티 재생성 대신 기존 엔티티 사용**
    현재 코드에서는 userId를 기반으로 새로운 user 객체를 빌더 패턴으로 생성하고 있습니다. 그러나, 이 방법은 효율적이지 않으며, 데이터베이스의 다른 필드가 유지되지 않을 수 있는 문제가 있습니다.
    기존 엔티티를 로드한 후 그 엔티티의 필드만 업데이트하는 것이 더 적절합니다. 이렇게 하면 엔티티의 나머지 필드가 유지되고, JPA의 더 나은 변경 추적 기능을 활용할 수 있습니다.
*/

    // 유저 정보 수정 구현
    @Transactional
    public Long updateUser(UserDto userDto) {
        Long id = userDto.getId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. ID: " + id));

        user.update(userDto);

        // userRepository.save(user);  // 필요 없음, JPA가 자동으로 변경사항을 반영

        return id;
    }


    // 유저 삭제 기능
    @Transactional
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
