package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Expense;

public interface ExpenseRepository {
    Iterable<Expense> findAll();

    Iterable<Expense> findAllByWalletId(Long id);

    Expense findById(Long id);

    Expense save(Expense expense);

    Integer deleteByWalletId(Long walletId);
}
