package com.meminator.userModule.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.meminator.userModule.models.UserToken;

@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Long>{

}
