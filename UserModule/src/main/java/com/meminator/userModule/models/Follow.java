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
	private RegisteredUser user;

	@NotNull
	@ManyToOne
	private RegisteredUser followedUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public RegisteredUser getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(RegisteredUser followedUser) {
		this.followedUser = followedUser;
	}

	public Follow() {
	}
	
}
