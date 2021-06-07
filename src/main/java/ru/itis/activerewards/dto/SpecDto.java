package ru.itis.activerewards.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.activerewards.models.UserSpec;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SpecDto {
    private Long id;
    private String name;
    private UserDto sender;

    public static SpecDto from(UserSpec spec) {
        return SpecDto.builder()
                .id(spec.getId())
                .name(spec.getName())
                .build();
    }

    public static List<SpecDto> from(List<UserSpec> specs) {
        if (specs == null) return null;
        return specs.stream()
                .map(SpecDto::from)
                .collect(Collectors.toList());
    }
}
