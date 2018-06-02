package com.meminator.userModule.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meminator.userModule.models.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long>{

	@Query("SELECT ru FROM RegisteredUser ru WHERE username = :username")
	RegisteredUser getByUsername(@Param("username") String username);

	@Query("SELECT ru FROM RegisteredUser ru")
	List<RegisteredUser> getAll();
	
	@Query("SELECT ru.username FROM RegisteredUser ru WHERE username LIKE %:username%")
	List<String> searchByUsername(@Param ("username") String username);
}
