package com.example.laboratorio5_grupoe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //Home
    @GetMapping(value = {"/","/login"})
    public String login(){

        return "inicio";
    }
}
