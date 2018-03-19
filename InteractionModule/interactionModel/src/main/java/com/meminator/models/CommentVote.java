package com.meminator.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CommentVote {

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
}
