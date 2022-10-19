package com.bsrvch.entape.controller;

import com.bsrvch.entape.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
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
    public String entapeMain(@AuthenticationPrincipal User user, Model model) {
        System.out.println(user.getId());
        System.out.println(user.getUserInfo().getId());
//        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//        model.addAttribute("username", username);
//        ResourceBundle s = ResourceBundle.getBundle("locale/auth",Locale.getDefault());
//        model.addAttribute("st",s);
        return "main";
    }
//    @GetMapping("/{loc}")
//    public String ChageLocale(@PathVariable(value = "loc") String loc, Model model) {
//        Locale.setDefault(Locale.forLanguageTag(loc));
//        return "redirect:/";
//    }

}
