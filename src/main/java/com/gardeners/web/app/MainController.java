package com.gardeners.web.app;

import com.gardeners.web.app.services.GardenerService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GardenerService gardenerService;

    @RequestMapping(value="/get-all-posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        //return information about all posts
        List<Post> postList = jdbcTemplate.query("select * from post;", new PostMapper());
        return ResponseEntity.ok().body(postList);
    }

    @PostMapping(value="/gardeners/create")
    public String createGardeners(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model
    ) {
        //TODO: check if gardener with this username/email already exists in DB

        boolean gardenerCreated = gardenerService
                .createNewGardener(email, username, getHashedPassword(password));
        model.addAttribute("message", gardenerCreated);
        return "gardener-registration";
    }

    public String getHashedPassword(String stringToHash) {
        return BCrypt.hashpw(stringToHash, BCrypt.gensalt());
    }

}
