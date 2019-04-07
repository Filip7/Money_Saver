package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Wallet;

public interface WalletRepository {
    Iterable<Wallet> findAll();
    Wallet findById(Long id);
    Wallet save(Wallet wallet);
    Wallet findByUserId(Long userId);
}
