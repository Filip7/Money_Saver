package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.Wallet;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import hr.java.web.milkovic.moneyapp.repository.ExpenseRepository;
import hr.java.web.milkovic.moneyapp.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/expenses")
@SessionAttributes({"typeOfExpense", "wallet"})
public class MoneyAppController {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    public MoneyAppController(ExpenseRepository expenseRepository, WalletRepository walletRepository) {

        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    @ModelAttribute("typeOfExpense")
    public TypeOfExpense[] getTypesOfExpenses() {
        log.info("Types of expenses fetched");

        return TypeOfExpense.values();
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        log.info("Controller /new");

        model.addAttribute("expense", new Expense());
        model.addAttribute("typeOfExpense", TypeOfExpense.values());

        return "insertExpense";
    }

    @GetMapping("/searchForm")
    public String searchForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("flag", "true");

        return "searchExpenses";
    }

    @PostMapping("/search")
    public String searchResults(Expense expense, Model model) {
        Optional<List<Expense>> expenses = expenseRepository.findByNameLikeIgnoreCase(expense.getName());

        expenses.ifPresent(expenseList -> model.addAttribute("expenses", expenseList));

        return "searchExpenses";
    }

    @PostMapping
    public String processForm(@Validated Expense expense, BindingResult errors, Wallet wallet, Model model) {
        log.info("Processing expense: {}", expense);

        if (errors.hasErrors()) {
            log.error("There are errors, stopped sending");
            return "insertExpense";
        }

        //expense.setWalletId(wallet.getId());
        expense.setWallet(wallet);
        expense = expenseRepository.save(expense);

        model.addAttribute("expense", expense);

        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String currentDate = date.format(pattern);

        model.addAttribute("currentDate", currentDate);

        wallet.getExpenses().add(expense);
        wallet.updateSum();
        wallet = walletRepository.save(wallet);

        model.addAttribute("wallet", wallet);

        return "expenseAccepted";
    }

    @GetMapping("/reset-wallet")
    public String resetWallet(SessionStatus sessionStatus, Wallet wallet) {
        log.info("Wallet reset");
        wallet.setSum(new BigDecimal(0));

        walletRepository.save(wallet);
        Integer numberOfDeletedExpenses = expenseRepository.deleteByWalletId(wallet.getId());

        log.info("Deleted {} expenses for user with id {}", numberOfDeletedExpenses, wallet.getUserId());

        sessionStatus.setComplete();

        return "redirect:/";
    }

}
