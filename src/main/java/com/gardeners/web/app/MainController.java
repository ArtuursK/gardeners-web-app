package com.gardeners.web.app;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value="/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("Hello from spring server");
    }
}
