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

@Entity
public class Comment {
	
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
	private Date commentDate; 
	
	String comment; 
	
	int upVoteCount; 
	int downVoteCount;
	
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
