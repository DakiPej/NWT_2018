package com.meminator.userModule.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.meminator.userModule.models.Follow;

@Repository
public interface FollowRepository extends CrudRepository<Follow, Long>{

}
