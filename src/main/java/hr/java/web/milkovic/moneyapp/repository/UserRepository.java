package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.MoneyAppUser;

public interface UserRepository {
    MoneyAppUser findByUserName(String userName);
}
