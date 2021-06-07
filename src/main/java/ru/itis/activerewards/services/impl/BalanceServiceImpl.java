package ru.itis.activerewards.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.activerewards.dto.BalanceInfo;
import ru.itis.activerewards.forms.SendMoneyForm;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.services.AuthenticationService;
import ru.itis.activerewards.services.BalanceService;
import ru.itis.activerewards.services.TransactionService;

import java.util.UUID;


@Service
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    @Transactional
    public void sendMoneyToUser(Authentication authentication, SendMoneyForm form) {
        if (form.getSum() <= 0) {
            throw new IllegalArgumentException("Sum must be > 0");
        }
        User sender = authenticationService.getUserModelByAuthentication(authentication);
        User receiver = usersRepository.getOne(form.getReceiverId());
        Double giftBalance = sender.getGiftBalance();

        if (giftBalance < form.getSum()) {
            throw new IllegalArgumentException("Not enough gift balance");
        }

        sender.setGiftBalance(giftBalance - form.getSum());
        receiver.setBalance(receiver.getBalance() + form.getSum());

        usersRepository.save(sender);
        usersRepository.save(receiver);

        transactionService.createTransaction(sender, receiver, form);
    }

    @Override
    public BalanceInfo getBalanceInfo(Authentication authentication) {
        User current = authenticationService.getUserModelByAuthentication(authentication);
        return BalanceInfo.builder()
                .balance(current.getBalance())
                .giftBalance(current.getGiftBalance())
                .build();
    }
}
