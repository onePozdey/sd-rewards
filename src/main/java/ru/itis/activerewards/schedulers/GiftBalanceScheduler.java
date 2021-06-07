package ru.itis.activerewards.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.activerewards.repositories.UsersRepository;


@Component
public class GiftBalanceScheduler {

    @Autowired
    private UsersRepository usersRepository;

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void topUpBalance() {
        usersRepository.topUpGiftBalanceToActiveUsers();
    }
}
