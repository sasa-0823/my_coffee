package com.example.my_coffee_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my_coffee_list.Form.SignupForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/signUp")
    public String signUp(Model model){
        model.addAttribute("signUp", new SignupForm());
        return "auth/signUp";
    }

    @PostMapping("/signUp")
    public String signUp_regist(@RequestBody String entity) {
        
        return entity;
    }
    
}
