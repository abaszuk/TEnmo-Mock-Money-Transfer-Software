package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TransferDao transferDao;

    private static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");


    private static final String API_BASE_PATH = "/Transfer";

    @PreAuthorize("permitAll")
    @RequestMapping(path = API_BASE_PATH + "/directory", method = RequestMethod.GET)
    public List<String> getUsernames() {
        return userDao.findAllUsernames();
    }

    @RequestMapping(path = API_BASE_PATH + "/send", method = RequestMethod.POST)
    public String send(@RequestParam String user, @RequestParam double amount, Principal principal) {
        int receiverId = userDao.findIdByUsername(user);
        int senderId = userDao.findIdByUsername(principal.getName());
        BigDecimal transferAmount = new BigDecimal(amount);
        Transfer transfer = null;
        boolean success = false;
        try {
            if (senderId != receiverId){
                if (transferAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (accountDao.getBalance(senderId).compareTo(BigDecimal.ZERO) > 0) {

                        accountDao.subtract(transferAmount, senderId);
                        accountDao.add(transferAmount, receiverId);
                        transfer = transferDao.send(transferAmount, senderId, receiverId);
                        success = true;

                    } else {
                        throw new Exception("Insufficient funds in account to complete send");
                    }
                } else {
                    throw new Exception("Send amount must be a positive number");
                }
            } else {
                throw new Exception("Cannot send funds to yourself");
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        if (success) {
            return "Transfer successful \n" + getLog(transfer);
        } else {
            return "Transfer unsuccessful";
        }

    }

    private String getLog(Transfer transfer) {

        String log = "";
        String sender = userDao.findUsernameById(transfer.getSenderId());
        String receiver = userDao.findUsernameById(transfer.getReceiverId());

        log += LOG_FORMAT.format(transfer.getReceiveTime()) + " " + sender + " sent $" +
                transfer.getTransferAmount() +
                " to " + receiver;

        log += ", transaction ID is " + transfer.getTransferId();

        if (!transfer.isCompleted() && !transfer.isRejected()) {
            log += ", transfer is pending";
        }

        if (transfer.isRejected()) {
            log += ", transfer has been rejected";
        }

        return log;

    }


}