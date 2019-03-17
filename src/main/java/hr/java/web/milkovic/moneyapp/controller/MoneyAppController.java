package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/expenses")
public class MoneyAppController {

    @GetMapping("/new")
    public String showForm(Model model){
        model.addAttribute("expense", new Expense());
        model.addAttribute("typesOfExpenses", TypeOfExpense.values());

        return "selectExpense";
    }

    @PostMapping
    public String processForm(Expense expense, Model model){
        model.addAttribute("expense", expense);

        return "expenseAccepted";
    }

}
