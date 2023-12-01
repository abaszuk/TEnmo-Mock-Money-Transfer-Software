package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcMessageDao implements MessageDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Message getMessage(){
        return null;
    }




    private Message mapRowToMessage(SqlRowSet rs) {
        Message message = new Message();
        message.setMessageId(rs.getInt("message_id"));
        message.setUserA(rs.getInt("user_a"));
        message.setUserB(rs.getInt("user_B"));
        message.setMessagerId(rs.getInt("messager_id"));
        message.setMessageContent(rs.getString("message_content"));
        message.setMessageTime(rs.getTimestamp("message_time").toLocalDateTime());

        return message;
    }
}
