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
	
	@Autowired
	public void setPostVoteD(PostVoteDAO postVoteDao)	{
		this.postVoteDao = postVoteDao; 
	}
	@Autowired
	public void setNotificationDao(NotificationDAO notificationDao) {
		this.notificationDao = notificationDao; 
	}
	@Autowired
	public void setPostDAO(PostDAO postDao)	{
		this.postDao = postDao; 
	}
	@Autowired 
	public void setRegisteredUserDAO(RegisteredUserDAO regUserDao)	{
		this.regUserDao = regUserDao; 
	}
	
	
	public String createPostVote (long postId, long voterId, boolean upVote)	{
		
		Post post = new Post(); 
		RegisteredUser regUser = new RegisteredUser(); 
		
		post = this.postDao.findPostById(postId); 
		regUser = this.regUserDao.getRegisteredUserById(voterId); 
		
		PostVote postVote = new PostVote(); 
		postVote.setPostId(post);
		postVote.setRegUserId(regUser);
		postVote.setUpVote(upVote);
		
		Notification notification = new Notification(); 
		notification.setUserId(
				regUserDao.getRegisteredUserById(
						post.getRegUserId()
						.getId()
						)
				);
		NotificationType notificationTypeId = new NotificationType((long) 3); 
		notificationTypeId.setTypeName("Post vote");
		notification.setNotificationTypeId(notificationTypeId);
		notification.setNotifierUsername(
				this.regUserDao.getRegisteredUserById(voterId)
					.getUsername()
				);
		notification.setCreationMoment(new Date());
		notification.setContet(String.valueOf(postId));
		notification.setChecked(false);
		
		if(this.postVoteDao.createPostVote(postVote))
			if(this.notificationDao.createNotification(notification))
				return "PostVote and Notification were created"; 
			else return "Notification was not created"; 
		return "Post vote was not created"; 
	}
}
