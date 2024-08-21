package study.basic_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.basic_board.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이름으로 검색! Containing은 부분 검색 기능
    List<User> findByUsernameContaining(String username);

    // 닉네임 중복 확인 메서드
    boolean existsByNickname(String nickname);
}
