package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/transfer/userlist", method = RequestMethod.GET)
    public List<User> getUsernames() {
        List<User> users = userDao.findAll();
        return users;
    }


}