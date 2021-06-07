package ru.itis.activerewards.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "ldap")
@Getter
@Setter
public class LDAPProperties {
    private String host;
    private String login;
    private String password;
    private Integer port;
}
