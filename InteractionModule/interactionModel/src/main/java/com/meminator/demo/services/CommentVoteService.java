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
	
	public String createCommentVote(long commentId, String voterUsername, boolean upVote)	{
		try {

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
				
				System.out.println("CommentVote JESTE null.");
				commentVote = new CommentVote(); 
				commentVote.setComment(comment);
				commentVote.setVoter(voter);
				commentVote.setUpVote(upVote);
				doubleVote = false; 
				
			}	else if(commentVote.getUpVote() != upVote)	{
				System.out.println("CommentVote NIJE null");
				commentVote.setUpVote(upVote);
				doesntExist = false; 
				doubleVote = false; 
			}
			if(!doubleVote && this.commentVoteDao.createCommentVote(commentVote))
				if(this.notificationService.createNotification(commentVote))	{
					this.commentService.updateVoteCount(commentId, upVote, doesntExist);
					return "CommentVote and Notification were created"; 
				}	else throw new IllegalArgumentException("Notification was not created");
			throw new IllegalArgumentException("Notification and post vote were not created.");
		} catch (Exception e) {
			throw e ; 
		}
	}
	
	public String deleteCommentVote(long commentId, String voterUsername)	{
		try {
			
			System.out.println("ID JE : " + commentId + "\n USERNAME JE : " + voterUsername);
			if(!this.registeredUserDao.userExists(voterUsername))
				throw new IllegalArgumentException("The user with the specified username does not exist.") ;
			if(!this.commentDao.existsById(commentId))
				throw new IllegalArgumentException("The comment with the specified id does not exist.") ; 
			
			Comment comment = this.commentDao.findCommentById(commentId) ; 
			RegisteredUser voter = this.registeredUserDao.getRegisteredUserByUsername(voterUsername) ; 
			CommentVote cv = this.commentVoteDao.findByCommentAndVote(comment, voter) ; 
			
			if(cv == null) throw new IllegalArgumentException("The user did not vote for the comment.") ;
			
			this.commentVoteDao.delete(cv.getId());
			
			if(cv.getUpVote()) comment.setUpVoteCount(comment.getUpVoteCount() - 1) ;
			else comment.setDownVoteCount(comment.getDownVoteCount() - 1) ;
			
			this.commentDao.create(comment) ; 
			
			return "You have unvoted for the comment." ; 
			
		} catch (Exception e) {
			throw e ; 
		}
		
	}
}
