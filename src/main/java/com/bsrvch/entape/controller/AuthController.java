package com.bsrvch.entape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class AuthController {
    @GetMapping("/reg")
    public String Registration(Model model) {
        ResourceBundle s = ResourceBundle.getBundle("locale/reg", Locale.getDefault());
        model.addAttribute("st",s);
        return "reg";
    }
    @PostMapping("/reg")
    public String blogPostAdd(@RequestParam String email, @RequestParam String password, Model model){
        //User user = new User(email,password);
        return "redirect:/main";
    }
}
