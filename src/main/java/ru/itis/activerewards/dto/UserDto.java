package ru.itis.activerewards.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.activerewards.models.User;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String role;
    private Double balance;
    private Double giftBalance;

    public static UserDto from(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole().name())
                .balance(user.getBalance())
                .giftBalance(user.getGiftBalance())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

}
