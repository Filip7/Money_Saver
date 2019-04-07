package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.Wallet;
import hr.java.web.milkovic.moneyapp.repository.JDBCWalletRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"typeOfExpense", "wallet"})
public class RootController {

    private final JDBCWalletRepositoryImpl walletRepository;

    public RootController(JDBCWalletRepositoryImpl walletRepository) {
        this.walletRepository = walletRepository;
    }

    @GetMapping("/")
    public String showStartPage(Model model) {
        Wallet firstWallet = walletRepository.findById(1L);
        model.addAttribute("wallet", firstWallet);

        return "index";
    }

}
