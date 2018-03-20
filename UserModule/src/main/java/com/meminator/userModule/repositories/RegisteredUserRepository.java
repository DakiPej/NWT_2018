package com.meminator.userModule.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meminator.userModule.models.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long>{

	@Query("SELECT ru FROM RegisteredUser ru WHERE username = :username")
	RegisteredUser getUserByUsername(@Param("username") String username);
}
