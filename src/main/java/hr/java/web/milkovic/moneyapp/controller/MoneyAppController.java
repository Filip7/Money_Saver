package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/expenses")
@SessionAttributes({"name", "amount", "typeOfExpense", "sum", "wallet"})
public class MoneyAppController {

    @GetMapping("/new")
    public String showForm(@NotNull Model model) {
        log.info("Controller /new");
        model.addAttribute("expense", new Expense());
        model.addAttribute("typesOfExpenses", TypeOfExpense.values());

        return "insertExpense";
    }

    @PostMapping
    public String processForm(@Validated Expense expense, Model model, Errors errors) {
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

        return "expenseAccepted";
    }

    @GetMapping("/reset-wallet")
    public String resetWallet(SessionStatus sessionStatus){
        sessionStatus.setComplete();

        return "redirect:/expenses/new";
    }

}
