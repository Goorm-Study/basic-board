package com.BasicBoard.goorm.repository;

import com.BasicBoard.goorm.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 제목 검색
    List<Board> findByTitleContaining(String title);

}
