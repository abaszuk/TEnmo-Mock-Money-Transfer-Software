package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BigDecimal send() {
        return null;
    }

    @Override
    public BigDecimal request() {
        return null;
    }

    @Override
    public BigDecimal approve() {
        return null;
    }

    @Override
    public List<Transfer> pending() {
        return null;
    }

    @Override
    public List<Transfer> log() {
        return null;
    }

    @Override
    public Transfer getTransfer() {
        return null;
    }

    @Override
    public List<Transfer> rejected() {
        return null;
    }
}
