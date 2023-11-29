package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getBalance(int userId);

    BigDecimal add(BigDecimal amount);

    BigDecimal subtract(BigDecimal amount);
}
