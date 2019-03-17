package hr.java.web.milkovic.moneyapp.model;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Expense {
    private String name;
    private BigDecimal sum;
    private TypeOfExpense typeOfExpense;
}
