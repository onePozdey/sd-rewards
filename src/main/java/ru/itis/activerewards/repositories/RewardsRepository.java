package ru.itis.activerewards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.activerewards.models.Reward;


@Repository
public interface RewardsRepository extends JpaRepository<Reward, Long> {
}
