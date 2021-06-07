package ru.itis.activerewards.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.activerewards.models.Reward;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class RewardDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;

    public static RewardDto from(Reward reward) {
        return RewardDto.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .description(reward.getDescription())
                .price(reward.getPrice())
                .imageUrl(reward.getImageUrl())
                .build();
    }

    public static List<RewardDto> from(List<Reward> rewards) {
        return rewards.stream()
                .map(RewardDto::from)
                .collect(Collectors.toList());
    }
}
