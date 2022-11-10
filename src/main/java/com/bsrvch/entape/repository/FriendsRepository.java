package com.bsrvch.entape.repository;

import com.bsrvch.entape.models.Friends;
import com.bsrvch.entape.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendsRepository extends CrudRepository<Friends,Long> {
    Friends findByUser1AndUser2(User user1, User user2);
    List<Friends> findAllByUser1(User user1);
    List<Friends> findAllByUser2(User user2);
}
