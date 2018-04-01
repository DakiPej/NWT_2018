package com.meminator.userModule.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meminator.userModule.models.Follow;
import com.meminator.userModule.models.RegisteredUser;

@Repository
public interface FollowRepository extends CrudRepository<Follow, Long>{
	
	@Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END "
			+ "FROM Follow f, RegisteredUser u, RegisteredUser fu "
			+ "WHERE f.user.id = u.id "
			+ "AND f.followedUser.id = fu.id "
			+ "AND u.username = :user "
			+ "AND fu.username = :friend ")
	Boolean frendshipExists(@Param("user") String user, @Param("friend") String friend);

	@Query("SELECT f "
			+ "FROM Follow f, RegisteredUser u, RegisteredUser fu "
			+ "WHERE f.user.id = u.id "
			+ "AND f.followedUser.id = fu.id "
			+ "AND u.username = :username "
			+ "AND fu.username = :friendUsername")
	Follow getFriendship(@Param ("username") String username, @Param("friendUsername") String friendUsername);

	@Query("SELECT friend "
			+ "FROM Follow f, RegisteredUser friend "
			+ "WHERE f.user.id = :userID "
			+ "AND f.followedUser.id = friend.id")
	List<RegisteredUser> getFriends(@Param ("userID") Long userID);
}
