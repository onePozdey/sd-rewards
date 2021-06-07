package ru.itis.activerewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"ru.itis.activerewards"})
@EnableJpaRepositories
@EnableScheduling
@EnableConfigurationProperties
public class ActiveRewardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActiveRewardsApplication.class, args);
    }

}
