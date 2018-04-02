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
	RegisteredUser poster; 
	
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
	public Post (RegisteredUser poster)	{
		this.poster = poster; 
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
	public RegisteredUser getPoster() {
		return poster;
	}
	public void setPoster(RegisteredUser regUserId) {
		this.poster = regUserId;
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
