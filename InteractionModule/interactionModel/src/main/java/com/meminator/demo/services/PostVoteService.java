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
		
	}
}
