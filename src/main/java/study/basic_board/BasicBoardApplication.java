package study.basic_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // Auditing 기능 활성화 -> BaseTimeEntity에서 LocalDateTime 자동 생성을 위함
public class BasicBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicBoardApplication.class, args);
	}

}
