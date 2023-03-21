package com.gardeners.app.services;

import com.gardeners.app.entities.Post;
import com.gardeners.app.mappers.GardenerPostsMapper;
import com.gardeners.app.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Post getPostById(long postId) {
        return jdbcTemplate.query(
                "select * from post " +
                        "where post.post_id = ?;", new PostMapper(), postId).get(0);
    }

    public List<Post> getAllPosts() {
        return jdbcTemplate.query(
                "select gardener.username, gardener.avatar_image_url, post.post_id, post.description, post.datetime, post.imageurl " +
                        "from post, gardener " +
                        "where gardener.gardener_id = post.gardener_id " +
                        "order by post.post_id desc;", new GardenerPostsMapper());
    }

    public List<Post> getAllPostsByUsername(String username) {
        return jdbcTemplate.query(
                "select gardener.username, gardener.avatar_image_url, post.post_id, post.description, post.datetime, post.imageurl " +
                        "from post, gardener " +
                        "where gardener.gardener_id = post.gardener_id and " +
                        "gardener.username = ?" +
                        "order by post.post_id desc;", new GardenerPostsMapper(), username);
    }
}
