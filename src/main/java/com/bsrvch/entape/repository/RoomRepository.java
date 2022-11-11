package com.bsrvch.entape.repository;

import com.bsrvch.entape.models.Room;
import com.bsrvch.entape.models.User;
import com.bsrvch.entape.models.UserRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room,Long> {
    Room findByUserRoomIn(List<UserRoom> users);
}
