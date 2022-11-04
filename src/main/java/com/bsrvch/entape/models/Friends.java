package com.bsrvch.entape.models;

import javax.persistence.*;

@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;
    private boolean userSuc1,userSuc2;

    public Friends() {
    }
/*
Вы можете определить два списка детей, один для отца и один для матери, которые являются двунаправленными отношениями.

Изменить:

@OneToMany(mappedBy="father")
private List<Student> children = new ArrayList<>();
Кому:

@OneToMany(mappedBy="mother")
private List<Student> childrenMother = new ArrayList<>();

@OneToMany(mappedBy="father")
private List<Student> childrenFather = new ArrayList<>();
 */
    public Friends(User user1, User user2) {
        this.user2 = user2;
        this.user1 = user1;
    }
    public Friends(User user1, User user2, boolean userSuc1,boolean userSuc2) {
        this.user2 = user2;
        this.user1 = user1;
        this.userSuc2 = userSuc2;
        this.userSuc1 = userSuc1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public boolean isUserSuc1() {
        return userSuc1;
    }

    public void setUserSuc1(boolean userSuc1) {
        this.userSuc1 = userSuc1;
    }

    public boolean isUserSuc2() {
        return userSuc2;
    }

    public void setUserSuc2(boolean userSuc2) {
        this.userSuc2 = userSuc2;
    }
}
