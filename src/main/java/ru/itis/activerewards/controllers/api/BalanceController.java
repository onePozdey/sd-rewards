package ru.itis.activerewards.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.activerewards.dto.BalanceInfo;
import ru.itis.activerewards.forms.SendMoneyForm;
import ru.itis.activerewards.services.BalanceService;


@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMoneyToUser(Authentication authentication, @RequestBody SendMoneyForm form) {
        balanceService.sendMoneyToUser(authentication, form);
    }

    @GetMapping("/info")
    public ResponseEntity<BalanceInfo> getBalanceInfo(Authentication authentication) {
        return ResponseEntity.ok(balanceService.getBalanceInfo(authentication));
    }
}
