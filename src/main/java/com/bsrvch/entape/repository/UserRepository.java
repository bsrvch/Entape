package com.bsrvch.entape.repository;

import com.bsrvch.entape.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
