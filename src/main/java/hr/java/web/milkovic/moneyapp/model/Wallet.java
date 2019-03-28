package hr.java.web.milkovic.moneyapp.model;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfWallet;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Wallet {
    private BigDecimal sum;
    private List<Expense> expenses;
    private TypeOfWallet typeOfWallet;

    public Wallet() {
        sum = new BigDecimal(0);
        expenses = new ArrayList<>();
    }

    public void updateSum() {
        sum = expenses.stream().map(Expense::getAmount).reduce(BigDecimal::add).get();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
}
