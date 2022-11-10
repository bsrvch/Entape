package com.bsrvch.entape.models;

import javax.persistence.*;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rooms room;

    public Messages() {
    }
    public Messages(User user,String text) {
        this.text = text;
        this.user = user;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public User getUser() {return user;}
    public void setUser(User user) {
        this.user = user;
    }
    public Rooms getRoom() {return room;}
    public void setRoom(Rooms room) {this.room = room;}
}
