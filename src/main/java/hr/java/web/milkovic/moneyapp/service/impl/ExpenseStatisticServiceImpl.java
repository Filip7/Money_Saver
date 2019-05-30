package hr.java.web.milkovic.moneyapp.service.impl;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.dto.ExpenseStatisticDTO;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import hr.java.web.milkovic.moneyapp.repository.ExpenseRepository;
import hr.java.web.milkovic.moneyapp.repository.WalletRepository;
import hr.java.web.milkovic.moneyapp.service.ExpenseStatisticService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class ExpenseStatisticServiceImpl implements ExpenseStatisticService {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    public ExpenseStatisticServiceImpl(ExpenseRepository expenseRepository,
                                       WalletRepository walletRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public ExpenseStatisticDTO calculateStatistic(TypeOfExpense typeOfExpense) {
        BigDecimal min, max, sum;

        List<Expense> expenses = expenseRepository.findAllByTypeOfExpense(typeOfExpense);
        if(expenses.isEmpty()){
            return ExpenseStatisticDTO.builder()
                    .max(BigDecimal.ZERO)
                    .min(BigDecimal.ZERO)
                    .sum(BigDecimal.ZERO)
                    .typeOfExpense(typeOfExpense)
                    .build();
        }

        Comparator<Expense> comparator = Comparator.comparing(Expense::getCost);

        max = expenses.stream()
                .max(comparator)
                .get()
                .getCost();
        min = expenses.stream()
                .min(comparator)
                .get()
                .getCost();
        sum = expenses.stream()
                .map(Expense::getCost)
                .reduce(BigDecimal::add)
                .get();

        return ExpenseStatisticDTO.builder()
                .max(max)
                .min(min)
                .sum(sum)
                .typeOfExpense(typeOfExpense)
                .build();
    }
}
