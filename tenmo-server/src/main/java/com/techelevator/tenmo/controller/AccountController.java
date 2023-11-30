package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TransferDao transferDao;
    private static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
    private static final String API_BASE_PATH = "/Account";



    @RequestMapping(path = API_BASE_PATH + "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDao.getBalance(userId);
    }


    @RequestMapping(path = API_BASE_PATH + "/history", method = RequestMethod.GET)
    public List<String> history(Principal principal) {
        List<String> history = new ArrayList<>();
        try {

            int userId = userDao.findIdByUsername(principal.getName());
            for (Transfer transfer : transferDao.history(userId)) {
                history.add(getLog(transfer));
            }
            return history;

        }catch (Exception e){
            System.out.println("something went wrong with history");
        }
        return history;
    }

    @RequestMapping(path = API_BASE_PATH + "/history/{id}", method = RequestMethod.GET)
    public String historyById(@PathVariable Integer id, Principal principal) {
        String history = "";
        int userId = userDao.findIdByUsername(principal.getName());
        try {

            if (transferDao.getTransferById(id) == null) {
                throw new Exception("No record of transaction for user");
            }

            if (transferDao.verifyUserInTransaction(id, userId)) {
                history = getLog(transferDao.getTransferById(id));
                return history;
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return history;
    }

    private String getLog(Transfer transfer) {

        String log = "";
        String sender = userDao.findUsernameById(transfer.getSenderId());
        String receiver = userDao.findUsernameById(transfer.getReceiverId());

        log += LOG_FORMAT.format(transfer.getReceiveTime()) + " " + sender + " sent $" +
                transfer.getTransferAmount() +
                " to " + receiver + ", transaction ID is " + transfer.getTransferId();

        return log;

    }


}
