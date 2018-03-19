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
	private RegisteredUser userID;

	@NotNull
	@ManyToOne
	private RegisteredUser followingUserID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getUserID() {
		return userID;
	}

	public void setUserID(RegisteredUser userID) {
		this.userID = userID;
	}

	public RegisteredUser getFollowingUserID() {
		return followingUserID;
	}

	public void setFollowingUserID(RegisteredUser followingUserID) {
		this.followingUserID = followingUserID;
	}
}
