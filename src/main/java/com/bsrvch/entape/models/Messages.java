package com.bsrvch.entape.models;

import javax.persistence.*;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String text;
    private String time;

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    public Messages() {
    }
    public Messages(User user,String text,String time) {
        this.text = text;
        this.user = user;
        this.time = time;
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
    public Room getRoom() {return room;}
    public void setRoom(Room room) {this.room = room;}
}
