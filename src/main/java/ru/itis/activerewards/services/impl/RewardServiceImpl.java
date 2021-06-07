package ru.itis.activerewards.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.activerewards.dto.RewardDto;
import ru.itis.activerewards.dto.RewardOrderDto;
import ru.itis.activerewards.forms.RewardForm;
import ru.itis.activerewards.models.Reward;
import ru.itis.activerewards.models.RewardOrder;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.repositories.RewardOrdersRepository;
import ru.itis.activerewards.repositories.RewardsRepository;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.services.AuthenticationService;
import ru.itis.activerewards.services.RewardService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class RewardServiceImpl implements RewardService {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RewardsRepository rewardsRepository;
    @Autowired
    private RewardOrdersRepository rewardOrdersRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Value("${storage.path}")
    private String storagePath;

    @Override
    public Reward addReward(Authentication authentication, RewardForm form) {
        User creator = authenticationService.getUserModelByAuthentication(authentication);
        Reward reward = Reward.builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .price(form.getPrice())
                .creator(creator)
                .build();
        if (form.getImage() != null) {
            String imageUrl = saveImage(form.getImage());
            reward.setImageUrl(imageUrl);
        }

        return rewardsRepository.save(reward);
    }

    public String saveImage(MultipartFile image) {
        String imageUrl = "/img/" + UUID.randomUUID() + image.getOriginalFilename();
        File img = new File(storagePath + imageUrl);
        try {
            Files.copy(image.getInputStream(), Path.of(img.getAbsolutePath()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot save image");
        }
        return imageUrl;
    }

    @Override
    public List<RewardDto> getAll() {
        List<Reward> rewards = rewardsRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
        return RewardDto.from(rewards);
    }

    @Override
    @Transactional
    public void orderReward(Authentication authentication, Long id) {
        Reward reward = rewardsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        User user = authenticationService.getUserModelByAuthentication(authentication);
        if (user.getBalance() >= reward.getPrice()) {
            createRewardOrder(user, reward);
            user.setBalance(user.getBalance() - reward.getPrice());
            usersRepository.save(user);
        } else throw new IllegalArgumentException("Not enough balance");
    }

    public RewardOrder createRewardOrder(User user, Reward reward) {
        RewardOrder order = RewardOrder.builder()
                .receiver(user)
                .reward(reward)
                .dateTime(LocalDateTime.now())
                .status(RewardOrder.RewardOrderStatus.WAITING)
                .build();
        return rewardOrdersRepository.save(order);
    }

    @Override
    public List<RewardOrderDto> getUserRewardOrders(Authentication authentication) {
        User currentUser = authenticationService.getUserModelByAuthentication(authentication);
        List<RewardOrder> orders = rewardOrdersRepository.findAllByReceiver(currentUser, Sort.by(Sort.Direction.DESC, "dateTime"));
        return RewardOrderDto.from(orders);
    }

    @Override
    public List<RewardOrderDto> getAllWaitingOrders() {
        return RewardOrderDto.from(rewardOrdersRepository.findAllByStatus(RewardOrder.RewardOrderStatus.WAITING));
    }

    @Override
    public void completeRewardOrder(Authentication authentication, Long id) {
        RewardOrder order = rewardOrdersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(RewardOrder.RewardOrderStatus.COMPLETED);
        rewardOrdersRepository.save(order);
    }
}
