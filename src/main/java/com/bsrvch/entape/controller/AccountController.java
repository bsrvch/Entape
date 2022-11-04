package com.bsrvch.entape.controller;

import com.bsrvch.entape.models.Role;
import com.bsrvch.entape.models.User;
import com.bsrvch.entape.models.UserInfo;
import com.bsrvch.entape.repository.UserInfoRepository;
import com.bsrvch.entape.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String userLogin(@AuthenticationPrincipal User user, Model model){if(user!=null){return "redirect:/"+user.getUserInfo().getLogin();}return "login";}

    @GetMapping("/registration")
    public String Registration(@AuthenticationPrincipal User user, Model model) {if(user!=null){return "redirect:/"+user.getUserInfo().getLogin();}return "registration";}

    @PostMapping("/registration")
    public String addUserrr(@AuthenticationPrincipal User user1, @RequestParam String username, @RequestParam String password, Model model){
        if(user1!=null){
            return "redirect:/"+user1.getUserInfo().getLogin();
        }
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
        return "redirect:/user_info_reg";
    }
    @GetMapping("/user_info_reg")
    public String entapeUserInfoReg(@AuthenticationPrincipal User user, Model model) {
        if(user.getUserInfo().getLogin()!=null){
            return "redirect:/"+user.getUserInfo().getLogin();
        }
        return "user_info_reg";
    }
    @PostMapping("/user_info_reg")
    public String entapeAddInfo(@AuthenticationPrincipal User user, @RequestParam String first_name, @RequestParam String second_name, @RequestParam String login, @RequestParam String email, Model model){
        if(user.getUserInfo().getLogin()!=null){
            return "redirect:/"+user.getUserInfo().getLogin();
        }
        UserInfo userInfo = userInfoRepository.findById(user.getId()).orElseThrow();
        userInfo.setFirst_name(first_name);
        userInfo.setSecond_name(second_name);
        userInfo.setLogin(login);
        userInfo.setEmail(email);
        user.setUserInfo(userInfo);
        userInfoRepository.save(userInfo);
        return "redirect:/"+login;
    }
}
