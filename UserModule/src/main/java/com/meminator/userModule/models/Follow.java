package com.meminator.userModule.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Follow {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne
	private User userID;

	@NotNull
	@ManyToOne
	private User followingUserID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserID() {
		return userID;
	}

	public void setUserID(User userID) {
		this.userID = userID;
	}

	public User getFollowingUserID() {
		return followingUserID;
	}

	public void setFollowingUserID(User followingUserID) {
		this.followingUserID = followingUserID;
	}
}
