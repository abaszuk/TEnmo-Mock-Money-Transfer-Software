package com.techelevator.tenmo.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;

    @Override
    public BigDecimal getBalance(int userId) {
        BigDecimal balance = null;
        String sql = "Select balance\n" +
                "From account\n" +
                "Where user_id = ?;";
        try {
            balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

        } catch (DataAccessException e){

        }
        return balance;
    }

    @Override
    public BigDecimal add(BigDecimal amount) {
        return null;
    }

    @Override
    public BigDecimal subtract(BigDecimal amount) {
        return null;
    }
}
