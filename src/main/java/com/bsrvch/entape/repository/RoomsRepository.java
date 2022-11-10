package com.bsrvch.entape.repository;

import com.bsrvch.entape.models.Rooms;
import com.bsrvch.entape.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomsRepository extends CrudRepository<Rooms,Long> {
    Rooms findByUsersIn(List<User> users);
}
