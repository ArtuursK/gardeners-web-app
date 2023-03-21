package com.gardeners.app.controllers;


import com.gardeners.app.services.FileStorageService;
import com.gardeners.app.services.PostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    PostsService postsService;

    @Autowired
    FileStorageService fileStorageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    /**
     * entry endpoint into app
     * Note: May be redirected to login if not logged in
     * @return allposts page
     */
    @RequestMapping(value ="/")
    public String webAppRoot() {
        return "redirect:/allposts";
    }

    /**
     * An endpoint used to allow publicly available image download (that are not behind (protected by) a login)
     * Note: Loads images from root storage instead of "/resources/static/images..."
     * @param filename - name of the file that is being retrieved
     * @return ResponseEntity with a file
     */
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String filename) {
        Resource file = fileStorageService.load(filename);
        if(file != null)
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.badRequest().body(null);
    }

    /**
     * An endpoint used to allow access to images that are behind (protected by) a login
     * Note: Loads images from root storage instead of "/resources/static/images..."
     * @param username - logged in username
     * @param filename - name of the file that is being retrieved
     * @return ResponseEntity with a file
     */
    @GetMapping("/images/{username:.+}/{filename:.+}")
    public ResponseEntity<Resource> getUserImage(
            @PathVariable String username,
            @PathVariable String filename
    ) {
        Resource file;
        //if user is not logged in - allow only to access public images - forbid accessing username/... folder contents directly
        // a way of checking if the user in current context is logged in
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken)
        ) {
            String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
            //LOGGER.debug("Current authenticated user: " + loggedInUser);
            file = fileStorageService.load(filename, username);
        } else {
            file = fileStorageService.load(filename);
        }

        if(file != null)
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.badRequest().body(null);
    }


}
