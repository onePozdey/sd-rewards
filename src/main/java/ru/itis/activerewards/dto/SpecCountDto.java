package ru.itis.activerewards.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.activerewards.repositories.SpecCountView;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class SpecCountDto {
    private String name;
    private Integer count;

    public static List<SpecCountDto> from(List<SpecCountView> views) {
        return views.stream()
                .map(x ->
                        SpecCountDto.builder()
                                .count(x.getCount())
                                .name(x.getName())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
