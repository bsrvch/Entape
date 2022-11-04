package com.bsrvch.entape.controller;

import com.bsrvch.entape.models.Friends;
import com.bsrvch.entape.models.Notify;
import com.bsrvch.entape.models.User;
import com.bsrvch.entape.models.UserInfo;
import com.bsrvch.entape.repository.UserInfoRepository;
import com.bsrvch.entape.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MainController {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String entapeMain(@AuthenticationPrincipal User user,Model model) {
        ResourceBundle s = ResourceBundle.getBundle("locale/auth",Locale.getDefault());
        model.addAttribute("st",s);
        model.addAttribute("userInfo", user.getUserInfo());
        return "main";
    }
    @GetMapping("/tape")
    public String entapeTape(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("userInfo", user.getUserInfo());
        model.addAttribute("page","tape");
        return "main";
    }
    @GetMapping("/friends")
    public String entapeFriends(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userInfo", user.getUserInfo());
        model.addAttribute("page","friends");
        return "main";
    }
    @GetMapping("/add_friend")
    public String entapeAddFriends(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userInfo", user.getUserInfo());
        model.addAttribute("page","add_friend");
        return "main";
    }
    @GetMapping("/notify")
    public String entapeNotify(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userInfo", user.getUserInfo());
        model.addAttribute("page","notify");
        return "main";
    }
    @GetMapping(path = "/{login}")
    public String entapeLoadUser(@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        model.addAttribute("userInfo", user.getUserInfo());
        model.addAttribute("page",login);
        return "main";
    }
    @GetMapping(path = "/tape",params = "lol")
    public String entapeTape(@RequestParam String lol,@AuthenticationPrincipal User user,Model model) {
        return "blocks/tape";
    }
    @GetMapping(path= "/friends",params = "lol")
    public String entapeFriends(@RequestParam String lol,@AuthenticationPrincipal User user, Model model) {
        if(user.getFriends()!=null){
            model.addAttribute("friends",user.getFriends().iterator());
        }
        return "blocks/friends";
    }
    @GetMapping(path= "/add_friend",params = "lol")
    public String entapeAddFriends(@RequestParam String lol,@AuthenticationPrincipal User user, Model model) {
        Iterable<UserInfo> users = userInfoRepository.findAll();
        Map<Long,String> friends_map = user.getUserInfo().getFriends();
        List<Friends> userFriends = user.getFriends();
        List<UserInfo> friends = new ArrayList<UserInfo>();
        userFriends.iterator().forEachRemaining((friend)-> friends.add(friend.getUser1().getUserInfo()));
        model.addAttribute("users",users);
        model.addAttribute("friends",friends);
        return "blocks/add_friend";
    }

    @GetMapping(path= "/notify",params = "lol")
    public String entapeNotify(@RequestParam String lol,@AuthenticationPrincipal User user, Model model) {



        model.addAttribute("notifies", user.getNotify().iterator());
        return "blocks/notify";
    }
    @GetMapping(path = "/{login}", params = "lol")
    public String entapeLoadUser(@RequestParam String lol,@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        if(user.getUserInfo().getLogin().equals(login)){
            model.addAttribute("my_account","Моя страница");
        }
        UserInfo userInfo = userInfoRepository.findByLogin(login);
        model.addAttribute("userInfo", userInfo);
        return "by_user";
    }
    @PostMapping(path = "/{login}", params = "add")
    public String entapeAddFriend(@RequestParam String add,@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        UserInfo friend = userInfoRepository.findByLogin(login);
        Friends friends = new Friends(user,friend.getUser());
        Hibernate.initialize(user.getFriends());
        user.addFriends(friends);
        Hibernate.initialize(user.getNotify());
        String gg = "<button onclick=\"open_page('../"+user.getUserInfo().getLogin()+"')\">"+user.getUserInfo().getFirst_name()+" "+user.getUserInfo().getSecond_name()+"   </button><button>Добавить</button>";
        Notify notify = new Notify("friend_req","Запрос в друзья", gg,friend.getUser());
        user.addNotify(notify);
        userRepository.save(user);
        return "redirect:/friends";
    }
    @PostMapping("/{login}")
    public @ResponseBody Map<Long, String[]> entapeLoadUser(@ModelAttribute(value = "userId") String userId, BindingResult result) {
        Iterable<UserInfo> us = userInfoRepository.findAll();
        Map<Long,String[]> mp = new HashMap<>();
        for (UserInfo value:us) {
            mp.put(value.getId(), new String[]{value.getFirst_name(), value.getSecond_name(), value.getLogin()});
        }
        return mp;
    }
//    public @ResponseBody UserInfo deleteKp(@ModelAttribute(value = "userId") String userId, BindingResult result) {
//        Iterable<UserInfo> us = userInfoRepository.findAll();
//        List<UserInfo> target = new ArrayList<>();
//        us.forEach(target::add);
//        System.out.println(target.get(0));
//        return target.get(0);
//    }

//    @PostMapping("/by/{login}")
//    public String gg(){
//        System.out.println("text");
//        return "by_user";
//    }
//    @GetMapping("/{loc}")
//    public String ChageLocale(@PathVariable(value = "loc") String loc, Model model) {
//        Locale.setDefault(Locale.forLanguageTag(loc));
//        return "redirect:/";
//    }

}
