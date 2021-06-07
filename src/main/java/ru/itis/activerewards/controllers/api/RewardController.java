package ru.itis.activerewards.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.activerewards.dto.RewardDto;
import ru.itis.activerewards.forms.RewardForm;
import ru.itis.activerewards.models.Reward;
import ru.itis.activerewards.services.RewardService;

import java.util.List;


@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReward(Authentication authentication,
                          @ModelAttribute RewardForm form) {
        rewardService.addReward(authentication, form);
    }

    @GetMapping
    public ResponseEntity<List<RewardDto>> getAllRewards() {
        return ResponseEntity.ok(rewardService.getAll());
    }

    @PostMapping("/{id}/order")
    public void orderReward(@PathVariable Long id, Authentication authentication) {
        rewardService.orderReward(authentication, id);
    }

    @PostMapping("/orders/{id}/complete")
    public void completeRewardOrder(@PathVariable Long id, Authentication authentication) {
        rewardService.completeRewardOrder(authentication, id);
    }

}
