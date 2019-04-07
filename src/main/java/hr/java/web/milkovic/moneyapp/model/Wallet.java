package hr.java.web.milkovic.moneyapp.model;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfWallet;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Wallet {
    private Long id;
    private BigDecimal sum;
    private List<Expense> expenses;
    private TypeOfWallet typeOfWallet;
    private Long userId;

    public Wallet() {
        sum = new BigDecimal(0);
        expenses = new ArrayList<>();
    }

    public void updateSum() {
        sum = expenses.stream().map(expense -> expense.getCost().negate()).reduce(BigDecimal::add).get();
    }

}
