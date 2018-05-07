package com.meminator.demo.services;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.CommentDAO;
import com.meminator.demo.dao.CommentVoteDAO;
import com.meminator.demo.dao.NotificationDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.Comment;
import com.meminator.demo.models.CommentVote;
import com.meminator.demo.models.RegisteredUser;

@Service
public class CommentVoteService {
	
	CommentVoteDAO commentVoteDao; 
	CommentDAO commentDao; 
	RegisteredUserDAO registeredUserDao;
	NotificationService notificationService; 
	CommentService commentService;
	
	@Autowired
	public void setCommentVoteD(CommentVoteDAO commentVoteDao)	{
		this.commentVoteDao = commentVoteDao;
	}
	@Autowired
	public void setCommentDao(CommentDAO commentDao)	{
		this.commentDao = commentDao;
	}
	@Autowired
	public void setRegisteredUserDao(RegisteredUserDAO registeredUserDao)	{
		this.registeredUserDao = registeredUserDao;
	}
	@Autowired
	public void setNotificationService(NotificationService notificationService)	{
		this.notificationService = notificationService;
	}
	@Autowired
	public void setCommentService(CommentService commentService)	{
		this.commentService = commentService; 
	}
	
	public String createCommentVote(long commentId, String voterUsername, boolean upVote)	
		throws ServletException	{
		if(!this.registeredUserDao.userExists(voterUsername))
			throw new IllegalArgumentException("The user with the specified username does not exist.");
		if(!this.commentDao.existsById(commentId))
			throw new IllegalArgumentException("The comment with the specified id does not exist.");
		
		Comment comment = new Comment(); 
		RegisteredUser voter = new RegisteredUser(); 
		CommentVote commentVote = new CommentVote(); 
		
		comment = this.commentDao.findCommentById(commentId);
		voter = this.registeredUserDao.getRegisteredUserByUsername(voterUsername);
		commentVote = this.commentVoteDao.findByCommentAndVote(comment, voter);
		
		boolean doesntExist = true, doubleVote = true; 
		
		if(commentVote == null)	{
			
			commentVote = new CommentVote(); 
			commentVote.setComment(comment);
			commentVote.setVoter(voter);
			commentVote.setUpVote(upVote);
			doubleVote = false; 
			
		}	else if(commentVote.getUpVote() != upVote)	{
			commentVote.setUpVote(upVote);
			doesntExist = false; 
			doubleVote = false; 
		}
		if(!doubleVote && this.commentVoteDao.createCommentVote(commentVote))
			if(this.notificationService.createNotification(commentVote))	{
				this.commentService.updateVoteCount(commentId, upVote, doesntExist);
				return "CommentVote and Notification were created"; 
			}	else throw new ServletException("Notification was not created");
		throw new ServletException("Notification and post vote were not created.");
	}
}
