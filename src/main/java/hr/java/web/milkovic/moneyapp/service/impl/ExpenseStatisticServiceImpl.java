package hr.java.web.milkovic.moneyapp.service.impl;

import hr.java.web.milkovic.moneyapp.model.dto.ExpenseStatisticDTO;
import hr.java.web.milkovic.moneyapp.repository.ExpenseRepository;
import hr.java.web.milkovic.moneyapp.repository.WalletRepository;
import hr.java.web.milkovic.moneyapp.service.ExpenseStatisticService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    public ExpenseStatisticDTO calculateStatistic() {
        BigDecimal min, max, sum = BigDecimal.ZERO;

        return null;
    }
}
