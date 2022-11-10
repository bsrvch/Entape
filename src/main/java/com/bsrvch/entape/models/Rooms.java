package com.bsrvch.entape.models;

import com.bsrvch.entape.repository.MessagesRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    List<Messages> messages = new ArrayList<>();

    public Rooms() {
    }

    public Rooms(String name, List<User> users) {
        this.name = name;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
