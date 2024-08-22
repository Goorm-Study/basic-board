package com.basic_board.repository;

import com.basic_board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<User, Long> {
}
