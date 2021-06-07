package ru.itis.activerewards.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.repositories.UsersRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = usersRepository.findOneByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Unable to find user with login - " + login));
        return new UserDetailsImpl(user);
    }
}
