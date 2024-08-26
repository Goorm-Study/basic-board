package study.basic_board.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserListResponseDto {

    private int totalPages;
    private int currentPage;
    private List<UserResponseDto> responseDtoList;
}
