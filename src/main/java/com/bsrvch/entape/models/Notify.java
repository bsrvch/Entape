package com.bsrvch.entape.models;

import javax.persistence.*;

@Entity
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String type,name,text;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Notify() {}

    public Notify(String type, String name, String text, User user) {
        this.type = type;
        this.name = name;
        this.text = text;
        this.user = user;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}
}
