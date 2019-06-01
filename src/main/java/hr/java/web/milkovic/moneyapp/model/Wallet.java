package hr.java.web.milkovic.moneyapp.model;

import hr.java.web.milkovic.moneyapp.model.enums.TypeOfWallet;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "WALLET")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal sum;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    private List<Expense> expenses;

    @Enumerated(EnumType.STRING)
    private TypeOfWallet typeOfWallet;
    private LocalDateTime createDate;
    private Long userId;

    public Wallet() {
        sum = new BigDecimal(0);
        expenses = new ArrayList<>();
    }

    public void updateSum() {
        sum = expenses.stream()
                .map(expense -> expense.getCost().negate())
                .reduce(BigDecimal::add)
                .get();
    }

}
