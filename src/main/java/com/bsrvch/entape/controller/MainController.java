package com.bsrvch.entape.controller;

import com.bsrvch.entape.models.*;
import com.bsrvch.entape.repository.*;
import com.bsrvch.entape.utils.NotifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private NotifyRepository notifyRepository;
    @Autowired
    private MessagesRepository messagesRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRoomRepository userRoomRepository;
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
    @GetMapping("/letters")
    public String entapeLetters(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userInfo", user.getUserInfo());
        model.addAttribute("page","letters");
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
    @GetMapping(path = "/{login}/dialog")
    public String entapeUserDialog(@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        model.addAttribute("userInfo", user.getUserInfo());
        model.addAttribute("page",login+ "/dialog");
        return "main";
    }
    @GetMapping(path = "/tape",params = "lol")
    public String entapeTape(@RequestParam String lol,@AuthenticationPrincipal User user,Model model) {
        return "blocks/tape";
    }
    @GetMapping(path= "/friends",params = "lol")
    public String entapeFriends(@RequestParam String lol,@AuthenticationPrincipal User user, Model model) {
        if(user.getFriends()!=null){
            user.updateFriends(friendsRepository);
            model.addAttribute("friends",user.getFriends().iterator());
        }
        return "blocks/friends";
    }
    @GetMapping(path= "/letters",params = "lol")
    public String entapeLetters(@RequestParam String lol,@AuthenticationPrincipal User user, Model model) {
        //создать таблицу room типа комната как диалог и привязать к ней сообщения!!!!
        return "blocks/letters";
    }
    @GetMapping(path= "/add_friend",params = "lol")
    public String entapeAddFriends(@RequestParam String lol,@AuthenticationPrincipal User user, Model model) {
        Iterable<UserInfo> users = userInfoRepository.findAll();
        List<Friends> userFriends = user.getFriends();
        List<UserInfo> friends = new ArrayList<UserInfo>();
        userFriends.iterator().forEachRemaining((friend)-> friends.add(friend.getUser1().getUserInfo()));
        model.addAttribute("users",users);
        model.addAttribute("friends",friends);
        return "blocks/add_friend";
    }
    //@Transactional
    @GetMapping(path= "/notify",params = "lol")
    public String entapeNotify(@RequestParam String lol,@AuthenticationPrincipal User user, Model model) {
        user.updateNotify(notifyRepository);
        List<Notify> notifies = user.getNotify();
        List<String> model_notifies = new ArrayList<>();
        for(Notify notify: notifies){
            if(notify.getType().equals("friend_req")){
                if(userInfoRepository.findByLogin(notify.getText())!=null){
                    model_notifies.add(new NotifyUtils().friend_req(notify,userInfoRepository.findByLogin(notify.getText())));
                }
            }
        }
        model.addAttribute("notifies", model_notifies.iterator());
        return "blocks/notify";
    }
    @PostMapping(path= "/notify",params = "del")
    public String entapeNotifyDelete(@RequestParam String del,@AuthenticationPrincipal User user, Model model) {
        notifyRepository.deleteById(Long.valueOf(del));
        user.updateNotify(notifyRepository);
        return "redirect:/friends";
    }
    @GetMapping(path = "/{login}", params = "lol")
    public String entapeLoadUser(@RequestParam String lol,@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        if(user.getUserInfo().getLogin().equals(login)){
            model.addAttribute("my_account","Моя страница");
        }
        UserInfo userInfo = userInfoRepository.findByLogin(login);
        model.addAttribute("userInfo1", userInfo);
        return "by_user";
    }
    @GetMapping(path = "/{login}/dialog", params = "lol")
    public String entapeUserDialog(@RequestParam String lol,@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(userInfoRepository.findByLogin(login).getUser());
        List<String> sortUsers = new ArrayList<>();
        sortUsers.add(users.get(0).getUserInfo().getLogin());
        sortUsers.add(users.get(1).getUserInfo().getLogin());
        Collections.sort(sortUsers);
        String roomName = sortUsers.get(0)+"_to_"+sortUsers.get(1);
        Room room =  roomRepository.findByName(roomName);//user.findRoomByUsers(roomName);
        if(room==null){
            System.out.println("dfghjk");
            room = new Room(roomName,users);
            roomRepository.save(room);
        }
        if(room.getMessages()!=null){
            List<Messages> messages = new ArrayList<>();
            messages = room.getMessages();
            messages.sort(new Comparator<Messages>() {
                              @Override
                              public int compare(Messages lhs, Messages rhs) {
                                  return Long.compare(lhs.getId(), rhs.getId());
                              }
                          }
            );
            model.addAttribute("messages", messages);
        }
        UserInfo userInfo = userInfoRepository.findByLogin(login);
        model.addAttribute("userInfo1", userInfo);
        model.addAttribute("room", room);
        if(room.getUserRoom().size()==2){
            model.addAttribute("roomName", userInfo.getFirst_name()+userInfo.getSecond_name());
        }
        else{
            model.addAttribute("roomName", room.getName());
        }
        model.addAttribute("userInfo", user.getUserInfo());
        return "blocks/dialog";
    }
//    @GetMapping(path = "/{login}/dialog", params = "lol")
//    public String entapeUserDialog(@RequestParam String lol,@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
//        user.updateMessages(messagesRepository);
//        User friend = userInfoRepository.findByLogin(login).getUser();
//        List<Messages> messages = new ArrayList<>(Stream.concat(messagesRepository.findAllByUser1AndUser2(user,friend).stream(), messagesRepository.findAllByUser1AndUser2(friend,user).stream()).toList());
//        if(messages!=null){
//            messages.sort(new Comparator<Messages>() {
//                              @Override
//                              public int compare(Messages lhs, Messages rhs) {
//                                  return Long.compare(lhs.getId(), rhs.getId());
//                              }
//                          }
//            );
//            model.addAttribute("messages", messages);
//        }
//        UserInfo userInfo = userInfoRepository.findByLogin(login);
//        model.addAttribute("userInfo1", userInfo);
//        model.addAttribute("userInfo", user.getUserInfo());
//        return "blocks/dialog";
//    }
    @PostMapping(path = "/{login}", params = "add")
    public String entapeAddFriend(@RequestParam String add,@AuthenticationPrincipal User user, @PathVariable(value = "login") String login, Model model){
        UserInfo friend = userInfoRepository.findByLogin(login);
        if(friendsRepository.findByUser1AndUser2(user,friend.getUser())!=null){
            return "redirect:/friends";
        }
        else if(friendsRepository.findByUser1AndUser2(friend.getUser(),user)!=null){
            Friends fre = friendsRepository.findByUser1AndUser2(friend.getUser(),user);
            fre.setUserSuc2(true);
            System.out.println(fre.isUserSuc1()+"  "+fre.isUserSuc2());
            friendsRepository.save(fre);
            return "redirect:/friends";
        }
        Friends friends = new Friends(user,friend.getUser());
        friends.setUserSuc1(true);
        user.addFriends(friends);
        userRepository.save(user);
        Notify notify = new Notify("friend_req","Запрос в друзья", user.getUserInfo().getLogin(),friend.getUser());
        friend.getUser().addNotify(notify);
        userRepository.save(friend.getUser());

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
    @PostMapping(path = "/update")
    public @ResponseBody String updateEntape(@AuthenticationPrincipal User user){
        if(user.getNotify().size()!=notifyRepository.findAllByUser(user).size()){
            return "notify";
        }

//        if(user.getReceivedMessages().size()!=messagesRepository.findAllByUser2(user).size()){
//            return "letters";
//        }
        return "none";
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
