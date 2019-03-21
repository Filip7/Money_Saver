package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/expenses")
public class MoneyAppController {

    @GetMapping("/new")
    public String showForm(@NotNull Model model) {
        log.info("Controller /new");
        model.addAttribute("expense", new Expense());
        model.addAttribute("typesOfExpenses", TypeOfExpense.values());

        return "insertExpense";
    }

    @PostMapping
    public String processForm(Expense expense, @NotNull Model model) {
        log.info("Controller POST");

        model.addAttribute("expense", expense);

        LocalDate date = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String currentDate = date.format(pattern);

        model.addAttribute("currentDate", currentDate);

        return "expenseAccepted";
    }

}
