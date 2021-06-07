package ru.itis.activerewards.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.activerewards.dto.TransactionDto;
import ru.itis.activerewards.forms.CommentForm;
import ru.itis.activerewards.forms.SpecForm;
import ru.itis.activerewards.services.TransactionService;
import ru.itis.activerewards.services.UserSpecsService;

import java.util.List;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserSpecsService specsService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelTransaction(@PathVariable Long id, @RequestBody CommentForm form, Authentication authentication) {
        transactionService.cancelTransaction(authentication, id, form);
    }

    @PostMapping("/specs")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSpec(@RequestBody SpecForm form) {
        specsService.addSpec(form);
    }
}
