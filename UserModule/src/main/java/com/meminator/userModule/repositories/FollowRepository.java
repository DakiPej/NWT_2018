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

	@Query("SELECT DISTINCT(friend.username) "
			+ "FROM Follow f, RegisteredUser user, RegisteredUser friend "
			+ "WHERE f.user.id = :userID "
			+ "AND f.followedUser.id = friend.id")
	List<String> getFriends(@Param ("userID") Long userID);

	@Query("SELECT DISTINCT(user.username) "
			+ "FROM Follow f, RegisteredUser me, RegisteredUser user "
			+ "WHERE me.id = :userID "
			+ "AND user.id = f.user.id "
			+ "AND f.followedUser = :userID")
	List<String> getFollowers(@Param ("userID") Long userID);
}
