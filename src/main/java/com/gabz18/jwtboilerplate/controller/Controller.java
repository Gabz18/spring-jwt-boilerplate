package com.gabz18.jwtboilerplate.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/hello")
    public String welcome(Authentication authentication) {

        return "hello";
    }
}
