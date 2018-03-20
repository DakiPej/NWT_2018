package com.meminator.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meminator.demo.models.RegisteredUser;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long>{

}
