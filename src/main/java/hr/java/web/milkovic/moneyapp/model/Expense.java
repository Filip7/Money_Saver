package hr.java.web.milkovic.moneyapp.model;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Getter
@Setter
public class Expense {

    @NotBlank(message = "Naziv ne smije biti prazno")
    private String name;

    @Min(value = 1, message = "Unesena vrijednost je premala")
    @Max(value = 10000, message = "Unesena vrijednost je prevelika")
    private BigDecimal amount;

    @NotBlank(message = "Vrijednost mora biti odabrana")
    private TypeOfExpense typeOfExpense;
}
