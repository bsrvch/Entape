package com.bsrvch.entape.repository;

import com.bsrvch.entape.models.Messages;
import com.bsrvch.entape.models.Rooms;
import com.bsrvch.entape.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Messages,Long> {
    List<Messages> findAllByUser(User user);
    List<Messages> findAllByRoom(Rooms room);

}
