package ru.itis.activerewards.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.activerewards.dto.UserDto;
import ru.itis.activerewards.services.*;


@Controller
public class PagesController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private UserSpecsService specsService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/profile")
    public String profilePage(Authentication authentication, Model model) {
        UserDto user = authenticationService.getUserByAuthentication(authentication);
        model.addAttribute("user", user);
        model.addAttribute("specs", specsService.getUserSpecs(user.getId()));
        model.addAttribute("transactions", transactionService.getUserToTransactions(user.getId()));
        return "pages/profile";
    }

    @GetMapping("/user/{id}")
    public String profilePage(Model model, @PathVariable Long id) {
        model.addAttribute("user", authenticationService.getUserById(id));
        model.addAttribute("specs", specsService.getUserSpecs(id));
        model.addAttribute("transactions", transactionService.getUserToTransactions(id));
        return "pages/profile";
    }

    @GetMapping("/transactions")
    public String getTransactionsPage(Model model, Authentication authentication) {
        model.addAttribute("transactions", transactionService.getAll());
        model.addAttribute("specs", specsService.getAllSpecs());
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("users", searchService.getAllUsers());
        return "pages/transactions";
    }

    @GetMapping("/canceled-transactions")
    public String getCanceledTransactionsPage(Model model, Authentication authentication) {
        model.addAttribute("transactions", transactionService.getAllCanceled());
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        return "pages/canceled-transactions";
    }

    @GetMapping("/rewards")
    public String getRewardsPage(Model model, Authentication authentication) {
        model.addAttribute("rewards", rewardService.getAll());
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        return "pages/rewards";
    }

    @GetMapping("/my-orders")
    public String getMyOrdersPage(Model model, Authentication authentication) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("orders", rewardService.getUserRewardOrders(authentication));
        return "pages/my-orders";
    }

    @GetMapping("/orders")
    public String getOrdersPage(Model model, Authentication authentication) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("orders", rewardService.getAllWaitingOrders());
        return "pages/orders";
    }

    @GetMapping("/specs")
    public String getSpecsPage(Model model, Authentication authentication) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("specs", specsService.getAllSpecs());
        return "pages/specs";
    }
}
