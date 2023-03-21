package com.gardeners.app.controllers;


import com.gardeners.app.entities.Gardener;
import com.gardeners.app.entities.Post;
import com.gardeners.app.services.CommonService;
import com.gardeners.app.services.FileStorageService;
import com.gardeners.app.services.GardenerService;
import com.gardeners.app.services.PostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
public class PostsController {

    @Autowired
    PostsService postsService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    GardenerService gardenerService;

    @Autowired
    CommonService commonService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostsController.class);

    private static final String IMAGES_DIR_PREFIX = "/images/";

    @RequestMapping(value ="/allposts")
    public String getAllPosts(Authentication authentication, Model model) {
        //Access the user that is logged in and set it in model attribute
        String loggedInUserName = authentication.getName();
        model.addAttribute("username", loggedInUserName);

        //get all posts for this user
        List<Post> allPosts = postsService.getAllPosts();

        model.addAttribute("userIsAdmin", commonService.loggedInUserIsAdmin());
        model.addAttribute("userAvatar", gardenerService.getGardenerByUsername(loggedInUserName).getAvatarImageUrl());
        model.addAttribute("caller", "allposts");
        model.addAttribute("posts", allPosts);
        return "allposts";
    }

    @RequestMapping(value ="/edit-post-form")
    public String showEditPostForm(
            Authentication authentication,
            Model model,
            @RequestParam("postDescription") String postDescription,
            @RequestParam("postId") long postId,
            @RequestParam("postImage") String postImage) {

        LOGGER.debug("Called an edit post form endpoint. Post ID: " + postId);
        Post post = new Post();
        post.setImageUrl(postImage);
        post.setDescription(postDescription);
        post.setId(postId);
        model.addAttribute("post", post);
        model.addAttribute("userAvatar", gardenerService.getGardenerByUsername(authentication.getName()).getAvatarImageUrl());
        model.addAttribute("userIsAdmin", commonService.loggedInUserIsAdmin());
        model.addAttribute("username", authentication.getName());
        return "edit-post-form";
    }

    @PostMapping(value ="/post/save")
    public String createNewPost(
            Authentication authentication,
            Model model,
            @RequestParam("postDescription") String postDescription,
            @RequestParam("file") MultipartFile imageFile
    ) {
        String loggedInUserName = authentication.getName();
        //gardener can only create new posts where he is the author
        Gardener gardener = gardenerService.getGardenerByUsername(loggedInUserName);
        //save file and get file path
        String fileName = IMAGES_DIR_PREFIX + loggedInUserName + "/" + fileStorageService.save(imageFile, loggedInUserName);

        //save post
        if(postsService.createNewPost(gardener.getId(), postDescription, fileName)) {
            model.addAttribute("message", "Your post was saved");
        } else {
            model.addAttribute("message", "Error when saving your post");
        }
        return getAllPosts(authentication, model);
    }
}
