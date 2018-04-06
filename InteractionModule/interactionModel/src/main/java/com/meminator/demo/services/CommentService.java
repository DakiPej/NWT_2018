package com.meminator.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.meminator.demo.dao.CommentDAO;
import com.meminator.demo.dao.PostDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.Comment;
import com.meminator.demo.models.Post;
import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.viewModels.CommentViewModel;

@Service
public class CommentService {
	
	CommentDAO commentDao; 
	PostDAO postDao; 
	RegisteredUserDAO registeredUserDao; 
	NotificationService notificationService; 
	
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
	@Autowired
	public void setNotificationService(NotificationService notificationService)	{
		this.notificationService = notificationService; 
	}
	public String createComment(long postId, String commenterUsername, String commentText)	{
		
		RegisteredUser commenter = this.registeredUserDao.getRegisteredUserByUsername(commenterUsername);
		Post post = this.postDao.findPostById(postId); 
		Comment comment = new Comment(); 
		
		if(commenter == null)
			return "The user does not exist"; 
		if(post == null)
			return "The post does not exist"; 
		
		comment.setPostId(post);
		comment.setUserCommenterId(commenter);
		comment.setCommentDate(new Date());
		comment.setComment(commentText);
		comment.setUpVoteCount(0);
		comment.setDownVoteCount(0);
		
		if(this.commentDao.createComment(comment))	{
			if(this.notificationService.createNotification(comment))
				return "Comment and notification created"; 
			return "Notification was not created"; 
			
		}
		return "Comment was not created"; 
	}
	
	public String editComment(long commentId, String commenterUsername, String commentText)	{
		
		Comment comment = this.commentDao.findCommentById(commentId); 
		
		if(!this.registeredUserDao.userExists(commenterUsername))
			return "The user does not exist"; 
		
		if(comment == null)
			return "The comment does not exist"; 
		
		if(comment.getUserCommenterId().getUsername() != commenterUsername)
			return "The user did not comment on the post"; 
		
		comment.setComment(commentText);
		
		if(this.commentDao.createComment(comment))
			return "The comment was edited";
		
		return "The comment was not edited"; 
	}
	
	public String deleteComment(String username, long commentId)	{
		
		Comment comment = this.commentDao.findCommentById(commentId); 
		if(comment != null)	{
			this.commentDao.deleteComment(comment); 
			return "Comment was deleted"; 
		}
		return "Comment was not deleted"; 
	}
	
	public List<Comment> getAllComments(long postId, String sortBy, int pageNumber)	{
		Pageable pageRequest = new PageRequest(pageNumber, 10, Sort.Direction.DESC, sortBy);
		
		return this.commentDao.getAllCommentsByPostId(
				this.postDao.findPostById(postId),
				pageRequest);
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
