package ru.itis.activerewards.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itis.activerewards.services.LDAPService;


@Component
@Profile("prod")
public class LDAPScheduler {

    @Autowired
    private LDAPService ldapService;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateUsers() {
        ldapService.uploadUsers();
    }

}
