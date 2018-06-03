package com.meminator.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.NotificationDAO;
import com.meminator.demo.dao.PostDAO;
import com.meminator.demo.dao.PostVoteDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.Notification;
import com.meminator.demo.models.NotificationType;
import com.meminator.demo.models.Post;
import com.meminator.demo.models.PostVote;
import com.meminator.demo.models.RegisteredUser;

@Service
public class PostVoteService {
	
	PostVoteDAO postVoteDao;
	NotificationDAO notificationDao;
	PostDAO postDao;
	RegisteredUserDAO regUserDao; 
	NotificationService notificationService;
	PostService postService; 
	
	@Autowired
	public void setPostVoteDao(PostVoteDAO postVoteDao)	{
		this.postVoteDao = postVoteDao; 
	}
	@Autowired
	public void setNotificationDao(NotificationDAO notificationDao) {
		this.notificationDao = notificationDao; 
	}
	@Autowired
	public void setPostDao(PostDAO postDao)	{
		this.postDao = postDao; 
	}
	@Autowired 
	public void setRegisteredUserDAO(RegisteredUserDAO regUserDao)	{
		this.regUserDao = regUserDao; 
	}
	@Autowired
	public void setNotificationService(NotificationService notificationService)	{
		this.notificationService = notificationService; 
	}
	@Autowired
	public void setPostService(PostService postService)	{
		this.postService = postService;
	}
	public String createPostVote (long postId, String voterUsername, boolean upVote)	{
		
		try {
			if(!this.postVoteDao.existsById(postId))	throw new IllegalArgumentException("The post does not exist.") ; 
			if(!this.regUserDao.userExists(voterUsername))	throw new IllegalArgumentException("The user does not exist.") ; 
			
			
			Post post = new Post(); 
			RegisteredUser voter = new RegisteredUser(); 
			PostVote postVote = new PostVote(); 
			
			post = this.postDao.findPostById(postId);
			voter = this.regUserDao.getRegisteredUserByUsername(voterUsername);
			postVote = this.postVoteDao.findByPostAndVoter(post, voter);
			
			boolean doesntExist = true, doubleVote = true; 
			
			if(postVote == null)	{
			
				postVote = new PostVote(); 
				postVote.setPost(post);
				postVote.setVoter(voter);
				postVote.setUpVote(upVote); 
				doubleVote = false; 
			
			}	else if(postVote.getUpVote() != upVote)	{
				postVote.setUpVote(upVote);
				doesntExist = false; 
				doubleVote = false; 
			
			}
			if(!doubleVote && this.postVoteDao.createPostVote(postVote))
				if(this.notificationService.createNotification(postVote))	{
			
					this.postService.updateVoteCount(postId, upVote, doesntExist);
					return "PostVote and Notification were created";
					
				}	else return "Notification was not created";
			
			return "PostVote and Notification were not created"; 
		
		} catch (Exception e) {
			throw e ; 
		}	
	}
	
	public String deletePostVote(long postId, String voterUsername)	{
		try {
			if(!this.postVoteDao.existsById(postId))	throw new IllegalArgumentException("The post does not exist.") ; 
			if(!this.regUserDao.userExists(voterUsername))	throw new IllegalArgumentException("The user does not exist.") ; 
			
			Post post = this.postDao.one(postId) ; 
			RegisteredUser voter = this.regUserDao.getRegisteredUserByUsername(voterUsername) ; 
			
			PostVote pv = this.postVoteDao.findByPostAndVoter(post, voter) ; 
			
			if(pv == null)	throw new IllegalArgumentException("The user did not vote for the post") ; 
			
			this.postVoteDao.delete(pv.getId()); 
			
			if(pv.getUpVote()) post.setUpVoteCount(post.getUpVoteCount() - 1);
			else	post.setDownVoteCount(post.getDownVoteCount() - 1);
			
			this.postDao.create(post) ; 
			
			return "You have unvoted for the post. " ; 
			
		} catch (Exception e) {
			throw e ; 
		}
	}
}
