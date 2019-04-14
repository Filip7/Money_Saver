package hr.java.web.milkovic.moneyapp.controller;

import hr.java.web.milkovic.moneyapp.model.MoneyAppUser;
import hr.java.web.milkovic.moneyapp.model.Wallet;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfWallet;
import hr.java.web.milkovic.moneyapp.repository.UserRepository;
import hr.java.web.milkovic.moneyapp.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Controller
@SessionAttributes({"typeOfExpense", "wallet"})
public class RootController {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public RootController(WalletRepository walletRepository,
                          UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showStartPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        MoneyAppUser logedInUser = userRepository.findByUserName(currentPrincipalName);

        log.info("Current user is {}, with id: {}", currentPrincipalName, logedInUser.getId());

        Optional<Wallet> userWallet = walletRepository.findByUserId(logedInUser.getId());
        if(userWallet.isPresent()){
            model.addAttribute("wallet", userWallet.get());
        } else {
            Wallet firstWallet = new Wallet();
            firstWallet.setTypeOfWallet(TypeOfWallet.PHYSICAL);
            firstWallet.setSum(new BigDecimal(0));
            firstWallet.setUserId(logedInUser.getId());
            firstWallet = walletRepository.save(firstWallet);

            model.addAttribute("wallet", firstWallet);
        }

        return "index";
    }

}
