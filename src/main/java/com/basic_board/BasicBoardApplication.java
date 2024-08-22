package com.basic_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BasicBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicBoardApplication.class, args);
	}

}
