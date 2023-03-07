package com.gardeners.web.app.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class GardenerService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean createNewGardener(
            String email,
            String username,
            String hashedPassword) {
        return jdbcTemplate.update("insert into gardener (email, username, password) values ?, ?, ?", email, username, hashedPassword) > 0;
    }
}
