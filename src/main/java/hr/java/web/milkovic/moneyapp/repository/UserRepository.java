package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.MoneyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MoneyAppUser, Long> {
    MoneyAppUser findByUsername(String userName);
}
