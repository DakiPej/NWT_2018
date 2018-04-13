package com.meminator.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

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
	public String createComment(long postId, String commenterUsername, String commentText)	
		throws ServletException		{
		
		if(!this.registeredUserDao.userExists(commenterUsername))
			throw new IllegalArgumentException("The user with the specified username does not exist.");
		if(!this.postDao.existsById(postId))
			throw new IllegalArgumentException("The post with the specified post id does not exist.");
		if(commentText.isEmpty())
			throw new IllegalArgumentException("The comment does not have any text");
		
		RegisteredUser commenter = this.registeredUserDao.getRegisteredUserByUsername(commenterUsername);
		Post post = this.postDao.findPostById(postId); 
		Comment comment = new Comment(); 
		
		comment.setPostId(post);
		comment.setUserCommenterId(commenter);
		comment.setCommentDate(new Date());
		comment.setComment(commentText);
		comment.setUpVoteCount(0);
		comment.setDownVoteCount(0);
		
		if(this.commentDao.createComment(comment))	{
			if(this.notificationService.createNotification(comment))
				return "Comment and notification created"; 
			throw new ServletException("Notification was not created"); 
		}
		throw new ServletException("The comment and notification were not created"); 
	}
	
	public String editComment(long commentId, String commenterUsername, String commentText)	
		throws ServletException		{
		
		if(!this.commentDao.exists(commentId))
			throw new IllegalArgumentException("The comment with the specified id does not exist.");
		if(!this.registeredUserDao.userExists(commenterUsername))
			throw new IllegalArgumentException("The user with the specified username does not exist.");
		if(commentText.isEmpty())
			throw new IllegalArgumentException("The comment text is empty.");
		
		Comment comment = this.commentDao.findCommentById(commentId); 
		
		if(comment.getUserCommenterId().getUsername() != commenterUsername)
			throw new IllegalArgumentException("The user did not leave the comment");
		
		comment.setComment(commentText);
		
		if(this.commentDao.createComment(comment))
			return "The comment was edited";
		
		throw new ServletException("The comment was not edited");
	}
	
	public String deleteComment(String username, long commentId)	{
		
		if(!this.commentDao.exists(commentId))
			throw new IllegalArgumentException("The comment with the specified id does not exist.");
		
		Comment comment = this.commentDao.findCommentById(commentId); 
		
		if(comment.getUserCommenterId().getUsername() != username)
			throw new IllegalArgumentException("The user did not leave the specified comment.");
		
		this.commentDao.deleteComment(comment); 
		return "Comment was deleted"; 
		
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
