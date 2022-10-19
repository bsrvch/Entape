package com.bsrvch.entape.controller;

import com.bsrvch.entape.models.Role;
import com.bsrvch.entape.models.User;
import com.bsrvch.entape.models.UserInfo;
import com.bsrvch.entape.repository.UserInfoRepository;
import com.bsrvch.entape.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @GetMapping("/login")
    public String userLogin(Model model){
        return "login";
    }

    @GetMapping("/registration")
    public String Registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUserrr(@RequestParam String username,@RequestParam String password, Model model){
        User user = new User(username,password);
        User userFromDb = userRepository.findByUsername(user.getUserName());
        if(userFromDb != null){
            model.addAttribute("message", "Пользователь с таким аккаунтом уже существует");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        user.setUserInfo(userInfo);
        userRepository.save(user);
        return "redirect:/main";
    }
}
