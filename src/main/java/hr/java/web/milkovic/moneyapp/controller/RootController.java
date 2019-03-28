package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Wallet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"typeOfExpense", "wallet"})
public class RootController {

    @GetMapping("/")
    public String showStartPage(Model model){
        model.addAttribute("wallet", new Wallet());

        return "index";
    }

}
