package study.basic_board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.basic_board.dto.UserDto;
import study.basic_board.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void nickname_충돌검사() {
        UserDto userDto = UserDto.builder()
                .username("test_username")
                .nickname("test_nickname")
                .birth(LocalDate.parse("2024-08-21"))
                .build();

        org.junit.jupiter.api.Assertions
                .assertDoesNotThrow(() -> userService.registerUser(userDto));

        Assertions.assertThatThrownBy(() -> userService.registerUser(userDto))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 존재하는 닉네임입니다.");
    }

    @Test
    void 같은_nickname_동시등록() throws InterruptedException {
        final String testNickname = "test_nickname";
        final int count = 3;
        final CountDownLatch countDownLatch = new CountDownLatch(count);

        List<RegisterUser> workers = new ArrayList<>();
        for(int i = 0; i < count; i++)
            workers.add(new RegisterUser("test_username", testNickname, LocalDate.parse("2024-08-21"),
                                        countDownLatch));

        workers.forEach(worker -> new Thread(worker).start());
        // TODO: 1개의 worker를 제외하고 모두 IllegalArgumentException 던지는지 확인
        countDownLatch.await();

        long numberOfUsers = userRepository.countByNickname(testNickname);
        Assertions.assertThat(numberOfUsers).isEqualTo(1);
    }

    private class RegisterUser implements Runnable {
        private String username;
        private String nickname;
        private LocalDate birth;

        private CountDownLatch countDownLatch;

        public RegisterUser(String username, String nickname, LocalDate birth, CountDownLatch countDownLatch) {
            this.username = username;
            this.nickname = nickname;
            this.birth = birth;

            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                UserDto userDto = UserDto.builder()
                        .username(username)
                        .nickname(nickname)
                        .birth(birth)
                        .build();

                userService.registerUser(userDto);
            } catch(IllegalArgumentException e) {
                throw e;
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}