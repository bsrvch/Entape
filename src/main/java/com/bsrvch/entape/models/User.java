package com.bsrvch.entape.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username, password;
    private boolean active;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Notify> notifies = new ArrayList<>();
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Friends> friends1 = new ArrayList<>();
    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Friends> friends2 = new ArrayList<>();
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}
    public void addNotify(Notify notify) {
        notifies.add(notify);
    }
    public void removeNotify(Notify notify) {
        notifies.remove(notify);
    }
    public void addFriends(Friends friend) {
        friends1.add(friend);
    }
    public void removeFriends(Friends friend) {
        friends1.remove(friend);
    }
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
    public Set<Role> getRoles() {return roles;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserName() {
        return username;
    }
    public void setUserName(String username) {this.username = username;}
    public UserInfo getUserInfo() {return userInfo;}
    public void setUserInfo(UserInfo userInfo) {this.userInfo = userInfo;}
    public List<Notify> getNotify() {return notifies;}
    public void setNotify(List<Notify> notifies) {this.notifies = notifies;}
    public List<Friends> getFriends() {
        List<Friends> friends = new ArrayList<>(friends1);
        for(Friends fr: friends2){
            Friends friend = new Friends(fr.getUser2(),fr.getUser1(),fr.isUserSuc2(),fr.isUserSuc1());
            friends.add(friend);
        }
        return friends;
    }
    public void setFriends(List<Friends> friends) {this.friends1 = friends;}
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {this.password = password;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
