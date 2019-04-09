package hr.java.web.milkovic.moneyapp.model;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
public class Expense {

    Long id;

    @NotBlank(message = "Naziv ne smije biti prazan")
    private String name;

    @NotNull(message = "Vrijednost mora biti unesena")
    @DecimalMin(value = "1", message = "Unesena vrijednost je premala")
    private BigDecimal cost;

    @NotNull(message = "Vrijednost mora biti odabrana")
    private TypeOfExpense typeOfExpense;

    private Long walletId;

    private LocalDateTime dateOfInsert;
}
