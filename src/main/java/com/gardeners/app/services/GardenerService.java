package com.gardeners.app.services;

import com.gardeners.app.entities.Gardener;
import com.gardeners.app.mappers.GardenerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GardenerService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Gardener getGardenerByUsername(String username) {
        List<Gardener> gardenerList = jdbcTemplate.query(
                "select * from gardener " +
                        "where gardener.username = ?;", new GardenerMapper(), username);
        return gardenerList.isEmpty() ? null : gardenerList.get(0);
    }

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean createNewGardener(String username, String email, String hashedPassword) {
        return jdbcTemplate.update(
                "insert into gardener (email, username, password, role, enabled) " +
                        "values (?, ?, ?, ?, ?)", email, username, hashedPassword, "ROLE_USER", 1) > 0;
    }
}
