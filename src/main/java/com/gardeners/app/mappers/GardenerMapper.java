package com.gardeners.app.mappers;

import com.gardeners.app.entities.Gardener;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GardenerMapper implements RowMapper<Gardener> {

    @Override
    public Gardener mapRow(ResultSet rs, int rowNum) throws SQLException {
        Gardener gardener = new Gardener();
        gardener.setId(rs.getInt("gardener_id"));
        gardener.setEmail(rs.getString("email"));
        gardener.setUsername(rs.getString("username"));
        gardener.setAvatarImageUrl(rs.getString("avatar_image_url"));
        return gardener;
    }


}
