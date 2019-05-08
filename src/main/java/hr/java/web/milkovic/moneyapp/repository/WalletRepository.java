package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findAll();

    Optional<Wallet> findById(Long id);

    Optional<Wallet> findByUserId(Long userId);

    void deleteWalletById(Long id);
}
