package com.gardeners.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value="/get-all-posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        //return information about all posts
        List<Post> postList = jdbcTemplate.query("select * from post;", new PostMapper());
        return ResponseEntity.ok().body(postList);
    }
}
