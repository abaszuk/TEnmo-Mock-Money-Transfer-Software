package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;

    private static final String API_BASE_PATH = "/Transfer";

    @PreAuthorize("permitAll")
    @RequestMapping(path = API_BASE_PATH + "/directory", method = RequestMethod.GET)
    public List<String> getUsernames() {
        return userDao.findAllUsernames();
    }

    @RequestMapping(path = API_BASE_PATH + "/send", method = RequestMethod.POST)
    public Transfer send(@PathVariable String user, @PathVariable double amount, Principal principal) {
        int receiverId = userDao.findIdByUsername(user);
        int senderId = userDao.findIdByUsername(principal.getName());
        BigDecimal transferAmount = new BigDecimal(amount);

        if (accountDao.getBalance(senderId).compareTo(new BigDecimal(0)) > 0) {



        }

    }

}