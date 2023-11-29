package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcAccountDao implements AccountDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account getAccount(int userId) {

        Account account = null;
        String sql = "Select account_id, user_id, balance\n" +
                "From account\n" +
                "Where user_id = ?;";
        try {
            SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql, userId);
            if(rowset.next()) {
                account = mapRowToAccount(rowset);
            }
        } catch (DataAccessException e){
            System.out.println("Something went wrong");
        }

        assert account != null;
        return account;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        BigDecimal balance = null;
        Account account = getAccount(userId);
        assert account != null;
        return account.getBalance();
    }

    @Override
    public BigDecimal add(BigDecimal amount) {
        return null;
    }

    @Override
    public BigDecimal subtract(BigDecimal amount) {
        return null;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }
}