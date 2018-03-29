package com.meminator.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Post {
	
	@Id
	long id; 
	
	@ManyToOne
	RegisteredUser regUserId; 
	
	@NotNull
	@Digits(integer=10, fraction=0)
	@Min(0)
	int upVoteCount; 
	@NotNull
	@Digits(integer=10, fraction=0)
	@Min(0)
	int downVoteCount; 
	
	public Post()	{
		
	}
	public Post (RegisteredUser regUser)	{
		this.regUserId = regUser; 
	}
	public Post(long id)	{
		this.id = id; 
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RegisteredUser getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(RegisteredUser regUserId) {
		this.regUserId = regUserId;
	}
	public int getUpVoteCount() {
		return upVoteCount;
	}
	public void setUpVoteCount(int upVoteCount) {
		this.upVoteCount = upVoteCount;
	}
	public int getDownVoteCount() {
		return downVoteCount;
	}
	public void setDownVoteCount(int downVoteCount) {
		this.downVoteCount = downVoteCount;
	}
}
