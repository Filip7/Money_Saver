package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.Wallet;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/expenses")
@SessionAttributes({"typeOfExpense", "wallet"})
public class MoneyAppController {

    @ModelAttribute("typeOfExpense")
    public TypeOfExpense[] getTypesOfExpenses(){
        log.info("Types of expenses fetched");

        return TypeOfExpense.values();
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        log.info("Controller /new");

        model.addAttribute("expense", new Expense());
        model.addAttribute("typeOfExpense", TypeOfExpense.values());
        //model.addAttribute("wallet", new Wallet());

        return "insertExpense";
    }

    @PostMapping
    public String processForm(@Validated Expense expense, BindingResult errors, Wallet wallet, Model model) {
        log.info("Processing expense: {}", expense);

        if(errors.hasErrors()){
            log.error("There are errors, stoping sending");
            return "insertExpense";
        }

        model.addAttribute("expense", expense);

        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String currentDate = date.format(pattern);

        model.addAttribute("currentDate", currentDate);

        wallet.getExpenses().add(expense);
        wallet.updateSum();
        model.addAttribute("wallet", wallet);

        return "expenseAccepted";
    }

    @GetMapping("/reset-wallet")
    public String resetWallet(SessionStatus sessionStatus){
        log.info("Wallet reset");
        sessionStatus.setComplete();

        return "redirect:/expenses/new";
    }

}
