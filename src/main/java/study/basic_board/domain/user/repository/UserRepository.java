package study.basic_board.domain.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.basic_board.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이름으로 검색! Containing은 부분 검색 기능
    Page<User>  findAll(Pageable pageable);
    Page<User>  findAllByUsername(Pageable pageable, String username);

    // 닉네임 중복 확인 메서드
    boolean existsByNickname(String nickname);
}
