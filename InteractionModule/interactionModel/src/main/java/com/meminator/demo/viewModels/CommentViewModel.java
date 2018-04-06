package com.meminator.demo.viewModels;

public class CommentViewModel	{
	
	public CommentViewModel(long commentId, String commenter, String commentText, int upVoteCount, int downVoteCount)	{
		this.commentId = commentId; 
		this.commenter = commenter; 
		this.commentText = commentText; 
		this.upVoteCount = upVoteCount; 
		this.downVoteCount = downVoteCount; 
	}
	
	public long commentId; 
	public String commenter; 
	public String commentText; 
	public int upVoteCount; 
	public int downVoteCount; 
}
