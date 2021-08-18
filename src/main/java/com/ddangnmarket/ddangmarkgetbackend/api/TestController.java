package com.ddangnmarket.ddangmarkgetbackend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello react";
    }

    @GetMapping("/login")
    public String login(){
        return "not logged in";
    }
}
