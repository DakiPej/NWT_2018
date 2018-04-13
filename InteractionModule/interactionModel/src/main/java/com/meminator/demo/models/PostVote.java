package com.meminator.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.meminator.demo.interfaces.iNotify;

@Entity
public class PostVote implements iNotify{

	@Id
	@GeneratedValue
	long id; 
	
	@ManyToOne
	Post post; 
	
	@ManyToOne 
	RegisteredUser voter; 
	
	@NotNull
	Boolean upVote; 
	
	public String getType()	{
		return "Post vote"; 
	}
	public String getPayload()	{
		return Long.toString(this.id);
	}
	public String getNotifier()	{
		return this.voter.getUsername(); 
	}
	public RegisteredUser getNotified()	{
		return this.post.getPoster();
	}
	public PostVote()	{
		
	}
	public PostVote(RegisteredUser regUser, Post post)	{
		this.voter = regUser; 
		this.post = post; 
	}
	public PostVote(long id)	{
		this.id = id; 
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post postId) {
		this.post = postId;
	}

	public RegisteredUser getVoter() {
		return voter;
	}

	public void setVoter(RegisteredUser voter) {
		this.voter = voter;
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
