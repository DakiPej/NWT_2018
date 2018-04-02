package com.meminator.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.meminator.demo.interfaces.iNotify;

@Entity
public class CommentVote implements iNotify{

	@Id
	@GeneratedValue
	long id; 
	
	@ManyToOne
	Comment comment; 
	
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
		return this.comment.getNotified();
	}
	public CommentVote()	{
		
	}
	public CommentVote(Comment comment, RegisteredUser voter)	{
		this.comment = comment; 
		this.voter = voter; 
	}
	public CommentVote(long id)	{
		this.id = id; 
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
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
