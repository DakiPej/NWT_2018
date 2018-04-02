package com.meminator.demo.models;


import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.meminator.demo.interfaces.iNotify;

@Entity
public class Comment implements iNotify{
	
	@Id
	@GeneratedValue
	long id; 
	
	@ManyToOne
	Post postId; 
	
	@ManyToOne
	RegisteredUser userCommenterId; 
	
	@Basic(optional=true)
	@Column(insertable=true)
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date commentDate; 
	
	@NotNull
	@Size(min=1, max=255)
	String comment; 
	
	@Digits(integer=10, fraction=0)
	@Min(0)
	int upVoteCount; 
	@Digits(integer=10, fraction=0)
	@Min(0)
	int downVoteCount;
	
	public String getType()	{
		return "Commented"; 
	}
	public String getNotifier()	{
		return this.userCommenterId.getUsername();
	}
	public String getPayload()	{
		return Long.toString(this.id); 
	}
	public RegisteredUser getNotified()	{
		return this.postId.getPoster();
	}
	
	public Comment()	{
	
	}
	public Comment (long id)	{
		this.id = id; 
	}
	public Comment (Post p, RegisteredUser rU)	{
		this.postId = p; 
		this.userCommenterId = rU; 
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Post getPostId() {
		return postId;
	}
	public void setPostId(Post postId) {
		this.postId = postId;
	}
	public RegisteredUser getUserCommenterId() {
		return userCommenterId;
	}
	public void setUserCommenterId(RegisteredUser userCommenterId) {
		this.userCommenterId = userCommenterId;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
