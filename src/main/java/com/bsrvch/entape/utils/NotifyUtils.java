package com.bsrvch.entape.utils;

import com.bsrvch.entape.models.Notify;
import com.bsrvch.entape.models.UserInfo;

public class NotifyUtils {
    public String friend_req(Notify notify, UserInfo userInfo){
        String login = userInfo.getLogin();
        String first_name = userInfo.getFirst_name();
        String second_name = userInfo.getSecond_name();
        String text = "<h1>"+notify.getName()+"</h1><br>" +
                "<button onclick=\"open_page('../"+login+"')\">"+ first_name +" "+second_name+"</button>" +
                "<button onclick=\"addFriend('../"+login+"','"+login+"',this);deleteNotify('../notify',"+notify.getId()+",this)\">Добавить</button>";
        return text;
    }
}
