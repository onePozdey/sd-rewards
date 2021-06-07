package ru.itis.activerewards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.models.UserSpec;

import java.util.List;


@Repository
public interface UserSpecsRepository extends JpaRepository<UserSpec, Long> {

    @Query("select count(spec) as count, spec.name as name " +
            "from Transaction t " +
            "left join t.specs as spec " +
            "where t.toUser = :user " +
            "group by spec.id")
    List<SpecCountView> getUserSpecs(User user);
}
