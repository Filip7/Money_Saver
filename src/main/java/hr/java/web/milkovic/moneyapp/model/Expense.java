package hr.java.web.milkovic.moneyapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXPENSE")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Naziv ne smije biti prazan")
    private String name;

    @NotNull(message = "Vrijednost mora biti unesena")
    @DecimalMin(value = "1", message = "Unesena vrijednost je premala")
    private BigDecimal cost;

    @NotNull(message = "Vrijednost mora biti odabrana")
    @Enumerated(EnumType.STRING)
    private TypeOfExpense typeOfExpense;

    /*private Long walletId;*/

    @ManyToOne
    @JoinColumn(name = "WALLETID")
    @JsonIgnore
    private Wallet wallet;

    private LocalDateTime dateOfInsert;
}
