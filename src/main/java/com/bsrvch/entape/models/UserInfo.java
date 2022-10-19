package com.bsrvch.entape.models;

import javax.persistence.*;

@Entity
public class UserInfo {
    @Id
    @Column(name = "user_id")
    private long id;
    private String info;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserInfo() {
    }

    public UserInfo(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
