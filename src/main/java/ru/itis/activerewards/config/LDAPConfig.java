package ru.itis.activerewards.config;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("prod")
public class LDAPConfig {

    @Autowired
    private LDAPProperties ldapProperties;

    @Bean
    public LDAPConnectionPool ldapConnectionPool() {
        try {
            return new LDAPConnectionPool(ldapConnection(), 1, 5);
        } catch (LDAPException e) {
            throw new IllegalArgumentException("Unable to create ldap connection pool");
        }
    }

    @Bean
    public LDAPConnection ldapConnection() {
        try {
            LDAPConnection connection = new LDAPConnection(
                    ldapProperties.getHost(),
                    ldapProperties.getPort(),
                    ldapProperties.getLogin(),
                    ldapProperties.getPassword()
            );
            return connection;
        } catch (LDAPException e) {
            throw new IllegalArgumentException("Unable to connect to ldap server");
        }
    }
}
