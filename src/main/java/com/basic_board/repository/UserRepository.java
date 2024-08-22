package com.basic_board.repository;

import com.basic_board.entity.Board;
import com.basic_board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
