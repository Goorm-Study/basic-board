package study.basic_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.basic_board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
