package hr.java.web.milkovic.moneyapp.repository;

import hr.java.web.milkovic.moneyapp.model.Expense;
import hr.java.web.milkovic.moneyapp.model.Wallet;
import hr.java.web.milkovic.moneyapp.model.enums.TypeOfWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
public class JDBCWalletRepositoryImpl implements WalletRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert walletInserter;
    private final JDBCExpenseRepositoryImpl expenseRepository;

    public JDBCWalletRepositoryImpl(JdbcTemplate jdbc, JDBCExpenseRepositoryImpl expenseRepository) {
        this.jdbc = jdbc;
        this.walletInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("wallet")
                .usingGeneratedKeyColumns("id");
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Iterable<Wallet> findAll() {
        return jdbc.query("select id, sum, typeOfWallet, createDate, userId from wallet", this::mapRowToWallet);
    }

    private Wallet mapRowToWallet(ResultSet resultSet, int i) throws SQLException {
        Wallet wallet = new Wallet();
        wallet.setId(resultSet.getLong("id"));
        wallet.setSum(resultSet.getBigDecimal("sum"));
        wallet.setTypeOfWallet(TypeOfWallet.valueOf(resultSet.getString("typeOfWallet")));
        Timestamp createDate = resultSet.getTimestamp("createDate");
        wallet.setCreateDate(createDate.toLocalDateTime());
        wallet.setUserId(resultSet.getLong("userId"));

        Iterable<Expense> it = expenseRepository.findAllByWalletId(wallet.getId());
        List<Expense> expenses = new ArrayList<>();
        for (Expense expense : it) {
            expenses.add(expense);
        }

        wallet.setExpenses(expenses);

        return wallet;
    }

    @Override
    public Wallet findById(Long id) {
        return jdbc.queryForObject("select id, sum, typeOfWallet, createDate, userId from wallet where id = ?",
                this::mapRowToWallet, id);
    }

    @Override
    public Wallet save(Wallet wallet) {
        wallet.setId(saveWalletDetails(wallet));

        return wallet;
    }

    private Long saveWalletDetails(Wallet wallet) {
        Map<String, Object> values = new HashMap<>();
        values.put("sum", wallet.getSum());
        values.put("typeOfWallet", wallet.getTypeOfWallet().toString());
        values.put("userId", wallet.getUserId());

        if (wallet.getId() != null) {
            update(wallet);
            return wallet.getId();
        } else {
            wallet.setCreateDate(LocalDateTime.now());
        }

        values.put("createDate", wallet.getCreateDate());

        return walletInserter.executeAndReturnKey(values).longValue();
    }

    private void update(Wallet wallet) {
        //language=SQL
        String sql = "update WALLET set SUM = ? where ID = ?";

        jdbc.update(sql, wallet.getSum(), wallet.getId());
    }

    @Override
    public Optional<Wallet> findByUserId(Long userId) {
        Wallet wallet;
        try {
            String sql = "select id, sum, typeOfWallet, createDate, userId from wallet where userId = ? limit 1";
            wallet = jdbc.queryForObject(sql, this::mapRowToWallet, userId);
        } catch (Exception e) {
            log.warn("First wallet not created: " + e.getMessage());
            wallet = null;
        }

        return Optional.ofNullable(wallet);
    }
}
