package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.MoneyAppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JDBCMoneyAppUserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert moneyAppUserInserter;

    public JDBCMoneyAppUserRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.moneyAppUserInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public MoneyAppUser findByUserName(String userName) {
        return jdbc.queryForObject("select ID, USERNAME from USERS where USERNAME=?",
                this::mapRowToMoneyAppUser, userName);

    }

    private MoneyAppUser mapRowToMoneyAppUser(ResultSet resultSet, int i) throws SQLException {
        MoneyAppUser moneyAppUser = new MoneyAppUser();
        moneyAppUser.setId(resultSet.getLong("id"));
        moneyAppUser.setUsername(resultSet.getString("username"));

        return moneyAppUser;
    }
}
