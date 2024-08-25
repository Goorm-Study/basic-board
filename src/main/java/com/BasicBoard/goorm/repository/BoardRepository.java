package com.BasicBoard.goorm.repository;

import com.BasicBoard.goorm.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
//    Optional<Board> findByBoardId(Long boardId);

    // 전체 검색 - 페이징
    Page<Board> findAll(Pageable pageable);

    // 제목으로 검색
    Page<Board> findBoardsByTitleContaining(Pageable pageable, String title);

    // 작성자 아이디로 검색
    Page<Board> findByUser_Nickname(Pageable pageable, String nickname);





}
