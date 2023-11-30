package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Override
    public Transfer send(BigDecimal amount, int senderId, int receiverId) {
        Transfer transfer = null;
        String sql = "INSERT INTO transfer_log(sender_id, receiver_id, transfer_amount, send_time,receive_time,is_completed,is_rejected)\n" +
                "VALUES (?,?,?,?,?,true,true) RETURNING transfer_id;";
        try{
           int transferId = jdbcTemplate.queryForObject(sql, int.class,senderId,receiverId,amount, LocalDate.now(),LocalDate.now());
           transfer = getTransferById(transferId);
        } catch (Exception e){
            System.out.println("something went wrong with a send");
        }
        return transfer;
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
        String sql = "SELECT *\n" +
                "FROM transfer_log\n" +
                "WHERE transfer_id = ?;";
        return null;
    }


    @Override
    public Transfer getTransferById(int transferId) {
        String sql = "SELECT *\n" +
                "FROM transfer_log\n" +
                "WHERE transfer_id = ?;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
            if (rowSet.next()) {
                return mapRowToTransfer(rowSet);
            }
        }catch (Exception e){
            System.out.println("something went wrong with get transfer by id");
        }
        return null;
    }

    @Override
    public List<Transfer> rejected() {
        return null;
    }
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setSenderId(rs.getInt("sender_id"));
        transfer.setReceiverId(rs.getInt("receiver_id"));
        transfer.setTransferAmount(rs.getBigDecimal("transfer_amount"));
        transfer.setSendTime(rs.getDate("send_time").toLocalDate());
        transfer.setReceiveTime(rs.getDate("receive_time").toLocalDate());
        transfer.setCompleted(rs.getBoolean("is_completed"));
        transfer.setRejected(rs.getBoolean("is_rejected"));
        return transfer;
    }
}
