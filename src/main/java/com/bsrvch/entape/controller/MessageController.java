package com.bsrvch.entape.controller;

import com.bsrvch.entape.models.Messages;
import com.bsrvch.entape.models.Room;
import com.bsrvch.entape.models.User;
import com.bsrvch.entape.repository.RoomRepository;
import com.bsrvch.entape.repository.UserInfoRepository;
import com.bsrvch.entape.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MessageController {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @PostMapping(value = "/{login}/dialog", params = "send")
    public @ResponseBody String sendMessage(@RequestParam String send, @AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(userInfoRepository.findByLogin(login).getUser());
        Room room = user.findRoomByUsers(users);
        if(room.getName()==null){
            System.out.println("dfghjk");
            room = new Room("dialog",users);
            roomRepository.save(room);

        }
        room.addMessages(new Messages(user,send));
        roomRepository.save(room);
        return "ok";
    }
//    @PostMapping(value = "/{login}/dialog", params = "send")
//    public @ResponseBody String sendMessage(@RequestParam String send, @AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
//        User friend = userInfoRepository.findByLogin(login).getUser();
//        user.addMessage(new Messages(user,friend,send));
//        userRepository.save(user);
//        userRepository.save(friend);
//        return "ok";
//    }
}
