package com.meminator.userModule.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meminator.userModule.models.UserType;

@Repository
public interface UserTypeRepository extends CrudRepository<UserType, Long>{
	
	@Query("SELECT ut FROM UserType ut WHERE id = :id")
	UserType findUserTypeById(@Param("id") Long id);
}
