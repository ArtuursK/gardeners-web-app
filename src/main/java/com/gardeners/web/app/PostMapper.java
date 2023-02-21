package com.gardeners.web.app;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPost_id(rs.getInt("post_id"));
        post.setDatetime(rs.getTimestamp("datetime"));
        post.setDescription(rs.getString("description"));
        post.setGardener_id(rs.getInt("gardener_id"));
        return post;
    }

}
