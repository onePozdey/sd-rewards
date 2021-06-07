package ru.itis.activerewards.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.services.LDAPService;
import ru.itis.activerewards.services.UserService;

import java.util.Collection;
import java.util.Optional;

@Component
@Profile("prod")
public class LDAPAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LDAPService ldapService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> optionalUser = usersRepository.findOneByLogin(login);

        if (optionalUser.isPresent() && optionalUser.get().isSuperAdmin()) {
        } else if (ldapService.isAuthenticated(login, password)) {
            if (optionalUser.isEmpty()) {
                userService.createUser(login, password);
            } else {
                userService.updatePassword(optionalUser.get(), password);
            }
        } else {
            throw new BadCredentialsException("Unable to ldap authenticate");
        }

        UserDetails details = userDetailsService.loadUserByUsername(login);
        Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
        return new UsernamePasswordAuthenticationToken(details, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
