package ru.itis.activerewards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.security.role.Role;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);

    @Modifying
    @Query("update User u set u.giftBalance = u.giftBalance + 100 where u.state = 'ACTIVE'")
    void topUpGiftBalanceToActiveUsers();

    boolean existsByLoginAndRole(String login, Role role);

    boolean existsByLogin(String login);

    @Modifying
    @Query("update User u set u.role = :role, u.state = :state where u.login = :login")
    void updateRoleByLogin(@Param("login") String login, @Param("role") Role role, @Param("state") User.UserState state);
}
