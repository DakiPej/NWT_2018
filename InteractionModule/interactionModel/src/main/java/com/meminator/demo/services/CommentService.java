package com.meminator.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.CommentDAO;
import com.meminator.demo.dao.PostDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.Comment;
import com.meminator.demo.models.Post;
import com.meminator.demo.models.RegisteredUser;

@Service
public class CommentService {
	
	CommentDAO commentDao; 
	PostDAO postDao; 
	RegisteredUserDAO registeredUserDao; 
	
	@Autowired
	public void setCommentDao(CommentDAO commentDao)	{
		this.commentDao = commentDao; 
	}
	@Autowired
	public void setPostDao(PostDAO postDao)	{
		this.postDao = postDao; 
	}
	@Autowired
	public void setRegisteredUserDao(RegisteredUserDAO registeredUserDao)	{
		this.registeredUserDao = registeredUserDao;
	}
	
	public String createComment(long postId, String commenterUsername, String commentText)	{
		
		RegisteredUser commenter = this.registeredUserDao.getRegisteredUserByUsername(commenterUsername);
		Post post = this.postDao.findPostById(postId); 
		Comment comment = new Comment(); 
		
		comment.setPostId(post);
		comment.setUserCommenterId(commenter);
		comment.setCommentDate(new Date());
		comment.setComment(commentText);
		comment.setUpVoteCount(0);
		comment.setDownVoteCount(0);
		
		if(this.commentDao.createComment(comment))
			return "Comment created"; 
		return "Comment not created"; 
	}
	public String deleteComment(String username, long commentId)	{
		
		Comment comment = this.commentDao.findCommentById(commentId); 
		if(comment != null)	{
			this.commentDao.deleteComment(comment); 
			return "Comment was deleted"; 
		}
		return "Comment was not deleted"; 
	}
	
	public void updateVoteCount(long commentId, boolean upVote, boolean doesntExist)	{
		Comment comment = this.commentDao.findCommentById(commentId); 
		
		if(doesntExist)	
			if(upVote)
				comment.setUpVoteCount(comment.getUpVoteCount() + 1);
			else
				comment.setDownVoteCount(comment.getDownVoteCount() + 1);
		else
			if(upVote)	{
				comment.setUpVoteCount(comment.getUpVoteCount() + 1);
				comment.setDownVoteCount(comment.getDownVoteCount() - 1);
			}
			else {
				comment.setUpVoteCount(comment.getUpVoteCount() - 1);
				comment.setDownVoteCount(comment.getDownVoteCount() - 1);
			}
		this.commentDao.createComment(comment);
	}
}
