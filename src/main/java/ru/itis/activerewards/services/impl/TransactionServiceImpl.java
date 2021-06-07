package ru.itis.activerewards.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.activerewards.dto.TransactionDto;
import ru.itis.activerewards.forms.CommentForm;
import ru.itis.activerewards.forms.SendMoneyForm;
import ru.itis.activerewards.models.Transaction;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.models.UserSpec;
import ru.itis.activerewards.repositories.TransactionsRepository;
import ru.itis.activerewards.repositories.UserSpecsRepository;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.services.TransactionService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private UserSpecsRepository specsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Transaction createTransaction(User from, User to, SendMoneyForm form) {
        Transaction transaction = Transaction.builder()
                .fromUser(from)
                .toUser(to)
                .sum(form.getSum())
                .dateTime(LocalDateTime.now())
                .status(Transaction.TransactionStatus.SUCCESS)
                .taskComment(form.getTaskComment())
                .build();

        if (form.getSpecsIds() != null && !form.getSpecsIds().isEmpty()) {
            transaction.setSpecs(specsRepository.findAllById(form.getSpecsIds()));
        }
        return transactionsRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> getAll() {
        return TransactionDto.from(transactionsRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTime")));
    }

    @Override
    @Transactional
    public void cancelTransaction(Authentication authentication, Long id, CommentForm form) {
        Transaction transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (transaction.getStatus() == Transaction.TransactionStatus.CANCELED) {
            throw new IllegalArgumentException("Transaction already canceled");
        }
        transaction.setCancelDateTime(LocalDateTime.now());
        transaction.setStatus(Transaction.TransactionStatus.CANCELED);
        transaction.setCancelComment(form.getComment());

        Double sum = transaction.getSum();
        User fromUser = transaction.getFromUser();
        User toUser = transaction.getToUser();
        fromUser.setGiftBalance(fromUser.getGiftBalance() + sum);
        toUser.setBalance(toUser.getBalance() - sum);

        usersRepository.save(fromUser);
        usersRepository.save(toUser);
        transactionsRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> getAllCanceled() {
        List<Transaction> canceledTransactions = transactionsRepository.findAllByStatus(Transaction.TransactionStatus.CANCELED,
                Sort.by(Sort.Direction.DESC, "cancelDateTime"));
        return TransactionDto.from(canceledTransactions);
    }

    @Override
    public List<TransactionDto> getUserToTransactions(Long id) {
        User user = usersRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        List<Transaction> transactions = transactionsRepository.findAllByToUser(user);
        return TransactionDto.from(transactions);
    }
}
