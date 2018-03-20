package com.meminator.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Post {
	
	@Id
	long id; 
	
	@ManyToOne
	RegisteredUser regUserId; 
	
	int upVoteCount; 
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
