package ru.itis.activerewards.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.activerewards.models.Transaction;
import ru.itis.activerewards.models.User;

import java.util.List;


@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByStatus(Transaction.TransactionStatus status, Sort sort);

    List<Transaction> findAllByToUser(User user);
}
