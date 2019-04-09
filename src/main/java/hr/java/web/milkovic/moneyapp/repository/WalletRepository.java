package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Wallet;

import java.util.Optional;

public interface WalletRepository {
    Iterable<Wallet> findAll();

    Wallet findById(Long id);

    Wallet save(Wallet wallet);

    Optional<Wallet> findByUserId(Long userId);
}
