package com.bsrvch.entape.repository;

import com.bsrvch.entape.models.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {
    UserInfo findByLogin(String login);
}
