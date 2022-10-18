package com.bsrvch.entape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class MainController {
    @GetMapping("/")
    public String AuthVerification(Model model) {
        ResourceBundle s = ResourceBundle.getBundle("locale/auth",Locale.getDefault());
        model.addAttribute("st",s);
        return "auth";
    }
    @GetMapping("/main")
    public String entapeMain(Model model) {
//        ResourceBundle s = ResourceBundle.getBundle("locale/auth",Locale.getDefault());
//        model.addAttribute("st",s);
        return "main";
    }
    @GetMapping("/{loc}")
    public String ChageLocale(@PathVariable(value = "loc") String loc, Model model) {
        Locale.setDefault(Locale.forLanguageTag(loc));
        return "redirect:/";
    }
}
