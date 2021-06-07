package ru.itis.activerewards.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.activerewards.models.RewardOrder;
import ru.itis.activerewards.models.User;

import java.util.List;


public interface RewardOrdersRepository extends JpaRepository<RewardOrder, Long> {
    List<RewardOrder> findAllByReceiver(User receiver, Sort sort);

    List<RewardOrder> findAllByStatus(RewardOrder.RewardOrderStatus status);
}
