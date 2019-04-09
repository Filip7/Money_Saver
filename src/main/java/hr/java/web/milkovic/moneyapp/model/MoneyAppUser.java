package hr.java.web.milkovic.moneyapp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MoneyAppUser {
    private Long id;
    private String username;
}
