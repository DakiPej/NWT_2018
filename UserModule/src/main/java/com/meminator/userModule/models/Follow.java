package com.meminator.userModule.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Follow {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JsonIgnore
	private RegisteredUser user;

	@NotNull
	@ManyToOne
	@JsonIgnore
	private RegisteredUser followedUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	@JsonIgnore
	public RegisteredUser getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(RegisteredUser followedUser) {
		this.followedUser = followedUser;
	}

	public Follow() {
	}
	
}
