package com.bsrvch.entape.models;

import com.bsrvch.entape.repository.MessagesRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    List<UserRoom> userRoom = new ArrayList<>();
//    @ManyToMany(fetch = FetchType.LAZY)
//    List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    List<Messages> messages = new ArrayList<>();

    public Room() {
    }

    public Room(String name, List<User> users) {
        this.name = name;
        for(User user:users){
            this.userRoom.add(new UserRoom(user,this));
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addUserRoom(UserRoom userRoom){
        this.userRoom.add(userRoom);
    }
    public List<UserRoom> getUserRoom() {
        return userRoom;
    }
    public void setUserRoom(List<UserRoom> userRoom) {
        this.userRoom = userRoom;
    }
    public void addMessages(Messages message){
        message.setRoom(this);
        this.messages.add(message);
    }
    public void updateMessages(MessagesRepository messagesRepository){
        this.messages = messagesRepository.findAllByRoom(this);
    }
    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

}
