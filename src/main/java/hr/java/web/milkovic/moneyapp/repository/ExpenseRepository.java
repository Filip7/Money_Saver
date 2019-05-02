package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Expense;

import java.util.Optional;

public interface ExpenseRepository {
    Iterable<Expense> findAll();

    Iterable<Expense> findAllByWalletId(Long id);

    Optional<Expense> findById(Long id);

    Expense save(Expense expense);

    Integer deleteByWalletId(Long walletId);

    void deleteExpenseById(Long id);
}
