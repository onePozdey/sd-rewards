package ru.itis.activerewards.services;

import org.springframework.security.core.Authentication;
import ru.itis.activerewards.dto.TransactionDto;
import ru.itis.activerewards.forms.CommentForm;
import ru.itis.activerewards.forms.SendMoneyForm;
import ru.itis.activerewards.models.Transaction;
import ru.itis.activerewards.models.User;

import java.util.List;


public interface TransactionService {
    Transaction createTransaction(User from, User to, SendMoneyForm sum);

    List<TransactionDto> getAll();

    void cancelTransaction(Authentication authentication, Long id, CommentForm form);

    List<TransactionDto> getAllCanceled();

    List<TransactionDto> getUserToTransactions(Long id);
}
