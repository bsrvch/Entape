package com.bsrvch.entape.models;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
public class UserInfo {
    @Id
    @Column(name = "user_id")
    private long id;
    private String first_name, second_name, login, email, friends;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    public UserInfo() {}
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public String getFirst_name() {return first_name;}
    public void setFirst_name(String first_name) {this.first_name = first_name;}
    public String getSecond_name() {return second_name;}
    public void setSecond_name(String second_name) {this.second_name = second_name;}
    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}



    public Map<Long,String> getFriends() {
        if(friends!=null){
            Map<Long,String> res = new HashMap<Long,String>();
            String str = friends.replaceAll("[>\\{\\} ]", "");
            String[] pairs = str.split(",");
            for (int i=0;i<pairs.length;i++) {
                String pair = pairs[i];
                String[] keyValue = pair.split("=");
                res.put(Long.valueOf(keyValue[0]),keyValue[1]);
            }
            return res;
        }
        return null;
    }
    public void setFriends(Map<Long,String> friends) {
        this.friends = friends.toString();
    }
}
