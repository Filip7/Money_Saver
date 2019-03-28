package hr.java.web.milkovic.moneyapp.model;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Getter
@Setter
public class Expense {

    @NotBlank(message = "Naziv ne smije biti prazan")
    private String name;

    @NotNull(message = "Vrijednost mora biti unesena")
    @DecimalMin(value = "1", message = "Unesena vrijednost je premala")
    private BigDecimal amount;

    @NotNull(message = "Vrijednost mora biti odabrana")
    private TypeOfExpense typeOfExpense;
}
