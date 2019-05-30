package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAll();

    List<Expense> findAllByWalletId(Long id);

    List<Expense> findAllByTypeOfExpense(TypeOfExpense typeOfExpense);

    Optional<Expense> findById(Long id);

    Integer deleteByWalletId(Long walletId);

    void deleteExpenseById(Long id);

    Optional<List<Expense>> findByNameLikeIgnoreCase(String name);
}
