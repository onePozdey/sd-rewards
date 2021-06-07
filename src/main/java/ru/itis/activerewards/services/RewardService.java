package ru.itis.activerewards.services;

import org.springframework.security.core.Authentication;
import ru.itis.activerewards.dto.RewardDto;
import ru.itis.activerewards.dto.RewardOrderDto;
import ru.itis.activerewards.forms.RewardForm;
import ru.itis.activerewards.models.Reward;

import java.util.List;


public interface RewardService {
    Reward addReward(Authentication authentication, RewardForm form);

    List<RewardDto> getAll();

    void orderReward(Authentication authentication, Long id);

    List<RewardOrderDto> getUserRewardOrders(Authentication authentication);

    List<RewardOrderDto> getAllWaitingOrders();

    void completeRewardOrder(Authentication authentication, Long id);
}
