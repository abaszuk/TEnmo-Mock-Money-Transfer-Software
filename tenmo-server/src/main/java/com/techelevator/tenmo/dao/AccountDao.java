package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getBalance(int userId);
    Account getAccount(int userId);

    BigDecimal add(BigDecimal amount);

    BigDecimal subtract(BigDecimal amount);
}
