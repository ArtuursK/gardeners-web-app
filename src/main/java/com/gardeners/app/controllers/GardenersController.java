package com.gardeners.app.controllers;

import com.gardeners.app.entities.Gardener;
import com.gardeners.app.services.GardenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GardenersController {

    @Autowired
    GardenerService gardenerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GardenersController.class);

    @RequestMapping(value ="/gardener-registration")
    public String showGardenerRegistrationPage() {
        return "gardener-registration";
    }

    @PostMapping(value = "/gardener/create")
    public String createNewGardener(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        LOGGER.debug("creating gardener with: " + username + " and password: " + password);

        //check if user with this username already exists
        Gardener gardener = gardenerService.getGardenerByUsername(username);
        if(gardener != null) {
            model.addAttribute("message", "User with this username already exists");
            return "gardener-registration";
        }

        if(gardenerService.createNewGardener(username, email, gardenerService.hashPassword(password))) {
            model.addAttribute("message", "User created");
        } else {
            model.addAttribute("message", "Error when creating user");
        }
        return "gardener-registration";
    }

}
