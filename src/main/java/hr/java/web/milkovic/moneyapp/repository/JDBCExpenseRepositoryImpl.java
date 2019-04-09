package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfExpense;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JDBCExpenseRepositoryImpl implements ExpenseRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert expenseInserter;

    public JDBCExpenseRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.expenseInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("expense")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Iterable<Expense> findAll() {
        return jdbc.query("select id, name, cost, dateOfInsert, typeOfExpense, walletId from expense",
                this::mapRowToExpense);
    }

    @Override
    public Iterable<Expense> findAllByWalletId(Long id) {
        return jdbc.query("select id, name, cost, dateOfInsert, typeOfExpense, walletId from expense where walletId = ?",
                this::mapRowToExpense, id);
    }

    private Expense mapRowToExpense(ResultSet resultSet, int i) throws SQLException {
        Expense expense = new Expense();
        expense.setId(resultSet.getLong("id"));
        expense.setName(resultSet.getString("name"));
        expense.setCost(resultSet.getBigDecimal("cost"));
        expense.setTypeOfExpense(TypeOfExpense.valueOf(resultSet.getString("typeOfExpense")));
        expense.setWalletId(resultSet.getLong("walletId"));
        Timestamp dateOfInsert = resultSet.getTimestamp("dateOfInsert");
        expense.setDateOfInsert(dateOfInsert.toLocalDateTime());

        return expense;
    }

    @Override
    public Expense findById(Long id) {
        return jdbc.queryForObject("select id, name, cost, dateOfInsert, typeOfExpense, walletId from expense where id = ?",
                this::mapRowToExpense, id);
    }

    @Override
    public Expense save(Expense expense) {
        expense.setDateOfInsert(LocalDateTime.now());
        expense.setId(saveExpenseDetails(expense));

        return expense;
    }

    private Long saveExpenseDetails(Expense expense) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", expense.getName());
        values.put("cost", expense.getCost());
        values.put("typeOfExpense", expense.getTypeOfExpense().toString());
        values.put("dateOfInsert", expense.getDateOfInsert());
        values.put("walletId", expense.getWalletId());

        return expenseInserter.executeAndReturnKey(values).longValue();
    }

    @Override
    public Integer deleteByWalletId(Long walletId) {
        String sql = "delete from EXPENSE where WALLETID=?";
        return jdbc.update(sql, walletId);
    }
}
