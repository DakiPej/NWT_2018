package com.meminator.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CommentVote {

	@Id
	@GeneratedValue
	long id; 
	
	@ManyToOne
	Comment commentId; 
	
	@ManyToOne
	RegisteredUser userCommentVoterId; 
	
	Boolean upVote;

	public Comment getCommentId() {
		return commentId;
	}

	public void setCommentId(Comment commentId) {
		this.commentId = commentId;
	}

	public RegisteredUser getUserCommentVoterId() {
		return userCommentVoterId;
	}

	public void setUserCommentVoterId(RegisteredUser userCommentVoterId) {
		this.userCommentVoterId = userCommentVoterId;
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
