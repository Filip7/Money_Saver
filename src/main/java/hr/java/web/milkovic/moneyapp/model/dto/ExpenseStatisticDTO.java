package hr.java.web.milkovic.moneyapp.model.dto;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseStatisticDTO {
    private TypeOfExpense typeOfExpense;
    private BigDecimal sum;
    private BigDecimal min;
    private BigDecimal max;
}
