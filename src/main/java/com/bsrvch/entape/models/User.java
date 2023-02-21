package com.bsrvch.entape.models;


import com.bsrvch.entape.repository.NotifyRepository;
import com.bsrvch.entape.repository.RoomRepository;
import com.bsrvch.entape.repository.UserRoomRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.bsrvch.entape.repository.FriendsRepository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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

//    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<Room> rooms = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<UserRoom> userRoom = new ArrayList<>();
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
    public void addNotify(Notify notify) {
        notifies.add(notify);
    }
    public void removeNotify(Long id) {
        for(Notify notify: notifies){
            if(notify.getId()==id){
                notifies.remove(notify);
                break;
            }
        }
    }
    public void updateNotify(NotifyRepository notifyRepository){
        setNotify(notifyRepository.findAllByUser(this));
    }
    public List<Notify> getNotify() {return notifies;}
    public void setNotify(List<Notify> notifies) {this.notifies = notifies;}
    public void addFriends(Friends friend) {
        friends1.add(friend);
    }
    public void removeFriends(Friends friend) {
        friends1.remove(friend);
    }
    public void updateFriends(FriendsRepository friendsRepository){
        setFriends1(friendsRepository.findAllByUser1(this));
        setFriends2(friendsRepository.findAllByUser2(this));
    }
    public List<Friends> getFriends() {
        List<Friends> friends = new ArrayList<>(friends1);
        for(Friends fr: friends2){
            Friends friend = new Friends(fr.getUser2(),fr.getUser1(),fr.isUserSuc2(),fr.isUserSuc1());
            friends.add(friend);
        }
        return friends;
    }
    public void setFriends1(List<Friends> friends) {this.friends1 = friends;}
    public void setFriends2(List<Friends> friends) {this.friends2 = friends;}
    public void addRoom(Room room){
        UserRoom userRoom = new UserRoom(this,room);
        this.userRoom.add(userRoom);
    }
    public List<Room> getRooms(){
        return this.userRoom.stream().map(UserRoom::getRoom).collect(Collectors.toList());
    }
    public void updateRooms(UserRoomRepository userRoomRepository){
        setUserRoom(userRoomRepository.findAllByUser(this));
    }
    public Room findRoomByUsers(String name){
        return this.getRooms().parallelStream().filter(room -> room.getName()==name).findFirst().orElse(null);
        //return this.getRooms().parallelStream().forEach(room1 -> room1.getUserRoom().stream().filter(userRoom1 -> userRoom1.getUser().equals(users.get(1))).findFirst().orElse(null));
        //return this.getRooms().parallelStream().filter(room1 -> room1.getUserRoom().stream().map(UserRoom::getRoom).collect(Collectors.toList()).equals(users)).findFirst().orElse(null);
    }
    public List<UserRoom> getUserRoom() {
        return userRoom;
    }

    public void setUserRoom(List<UserRoom> userRoom) {
        this.userRoom = userRoom;
    }

    //    public void addMessage(Messages message){sentMessages.add(message);}
//    public void updateMessages(MessagesRepository messagesRepository){
//        setSentMessages(messagesRepository.findAllByUser1(this));
//        setReceivedMessages(messagesRepository.findAllByUser2(this));
//    }
//    public List<Messages> getSentMessages() {return sentMessages;}
//    public void setSentMessages(List<Messages> sentMessages) {this.sentMessages = sentMessages;}
//    public List<Messages> getReceivedMessages() {return receivedMessages;}
//    public void setReceivedMessages(List<Messages> receivedMessages) {this.receivedMessages = receivedMessages;}
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
