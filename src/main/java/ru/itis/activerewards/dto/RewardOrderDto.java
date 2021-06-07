package ru.itis.activerewards.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.activerewards.utils.DateTimeUtils;
import ru.itis.activerewards.models.RewardOrder;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class RewardOrderDto {
    private Long id;
    private UserDto receiver;
    private RewardDto reward;
    private String status;
    private String dateTime;

    public static RewardOrderDto from(RewardOrder rewardOrder) {
        return RewardOrderDto.builder()
                .id(rewardOrder.getId())
                .receiver(UserDto.from(rewardOrder.getReceiver()))
                .reward(RewardDto.from(rewardOrder.getReward()))
                .status(rewardOrder.getStatus().name())
                .dateTime(rewardOrder.getDateTime().format(DateTimeUtils.dateTimeFormat))
                .build();
    }

    public static List<RewardOrderDto> from(List<RewardOrder> rewardOrders) {
        return rewardOrders.stream()
                .map(RewardOrderDto::from)
                .collect(Collectors.toList());
    }
}
