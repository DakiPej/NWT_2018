package com.meminator.userModule.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.meminator.userModule.models.UserType;

@Repository
public interface UserTypeRepository extends CrudRepository<UserType, Long>{

}
