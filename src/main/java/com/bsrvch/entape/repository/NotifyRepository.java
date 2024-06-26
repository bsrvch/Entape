package com.bsrvch.entape.repository;

import com.bsrvch.entape.models.Notify;
import com.bsrvch.entape.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotifyRepository extends CrudRepository<Notify,Long> {
    List<Notify> findAllByUser(User user);
}
