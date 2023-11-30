package com.techelevator.tenmo;

import com.techelevator.tenmo.dao.JdbcUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class PopulateDB {

    // TODO auto-script for populating DB
    public static void main(String[] args) {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();

        jdbcUserDao.create("user1", "password");
        jdbcUserDao.create("user2", "password");

    }

}
