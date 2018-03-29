package com.meminator.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class PostVote {

	@Id
	@GeneratedValue
	long id; 
	
	@ManyToOne
	Post postId; 
	
	@ManyToOne 
	RegisteredUser regUserId; 
	
	@NotNull
	Boolean upVote; 
	
	public PostVote()	{
		
	}
	public PostVote(RegisteredUser regUser, Post post)	{
		this.regUserId = regUser; 
		this.postId = post; 
	}
	public PostVote(long id)	{
		this.id = id; 
	}
	
	public Post getPostId() {
		return postId;
	}

	public void setPostId(Post postId) {
		this.postId = postId;
	}

	public RegisteredUser getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(RegisteredUser regUserId) {
		this.regUserId = regUserId;
	}

	public Boolean getUpVote() {
		return upVote;
	}

	public void setUpVote(Boolean upVote) {
		this.upVote = upVote;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
