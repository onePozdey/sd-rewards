package ru.itis.activerewards.services;

import org.springframework.security.core.Authentication;
import ru.itis.activerewards.dto.BalanceInfo;
import ru.itis.activerewards.forms.SendMoneyForm;


public interface BalanceService {
    void sendMoneyToUser(Authentication authentication, SendMoneyForm form);

    BalanceInfo getBalanceInfo(Authentication authentication);
}
