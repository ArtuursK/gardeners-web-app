package com.gardeners.app.mappers;

import com.gardeners.app.entities.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GardenerPostsMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setAuthorName(rs.getString("username"));
        post.setAuthorImageUrl(rs.getString("avatar_image_url"));
        post.setId(rs.getInt("post_id"));
        post.setDescription(rs.getString("description"));
        post.setDatetime(rs.getTimestamp("datetime"));
        post.setImageUrl(rs.getString("imageurl"));
        return post;
    }


}
