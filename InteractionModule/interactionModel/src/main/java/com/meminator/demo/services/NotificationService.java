package com.meminator.demo.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.NotificationDAO;
import com.meminator.demo.dao.NotificationTypeDAO;
import com.meminator.demo.dao.PostDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.interfaces.iNotify;
import com.meminator.demo.models.Notification;
import com.meminator.demo.models.NotificationType;
import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.viewModels.NotificationViewModel;

@Service("NotificationService")
public class NotificationService {
	
	NotificationDAO notificationDao; 
	NotificationTypeDAO notificationTypeDao; 
	RegisteredUserDAO registeredUseDao; 
	PostDAO postDao; 
	
	
	@Autowired
	public void setNotificationDao(NotificationDAO notificationDao)	{
		this.notificationDao = notificationDao; 
	}
	@Autowired 
	public void setNotificationTypeDao(NotificationTypeDAO notificationTypeDao)	{
		this.notificationTypeDao = notificationTypeDao;
	}
	@Autowired
	public void setRegisteredUserDao(RegisteredUserDAO registeredUserDao)	{
		this.registeredUseDao = registeredUserDao; 
	}
	@Autowired
	public void setPostDao(PostDAO postDao)	{
		this.postDao = postDao; 
	}
	
	private boolean validatateNotification(String notifierUsername, String username, String notificationType)	{
		
		boolean suggested = false; 
		
		if(notifierUsername.isEmpty() || notifierUsername.length() < 5 || notifierUsername.length() > 15 )
			return false; 
		if(username.isEmpty() || username.length() < 5 || username.length() > 15)
			return false; 
		if (notificationType.isEmpty())
			return false; 
		
		if	(notificationType.equals("Followed"))	
			suggested = true; 
		else if(notificationType.equals("Commented"))
			suggested = true; 
		else if(notificationType.equals("Post vote"))
			suggested = true; 
		else if(notificationType.equals("Comment vote"))
			suggested = true; 
		else if(notificationType.equals("Post repost"))
			suggested = true; 
		
		return suggested; 
		
	}
	
	public boolean createNotification(iNotify notify)	{
		
		Notification notification = new Notification(); 
		
		notification.setNotifierUsername(notify.getNotifier());
		notification.setContet(notify.getPayload());
		notification.setCreationMoment(new Date());
		notification.setNotificationTypeId(
			this.notificationTypeDao
			.getNotificationTypeByTypeName(notify.getType()
					)
			);
		notification.setUserId(notify.getNotified());
		notification.setChecked(false);
		return this.notificationDao.createNotification(notification);
	}
	/*public String createNotification(String notifierUsername, String username, String notificationType)	{
		Notification notification = new Notification(); 
		
		if(validatateNotification(notifierUsername, username, notificationType))	{
			notification.setCreationMoment(new Date());
			
			if	(notificationType.equals("Followed"))	
				notification.setContet("You haven been followed by " + notifierUsername + ".");
			else if(notificationType.equals("Commented"))
				notification.setContet("The user " + notifierUsername + " commented on your post.");
			else if(notificationType.equals("Post vote"))
				notification.setContet("The user " + notifierUsername + " voted for your post.");
			else if(notificationType.equals("Comment vote"))
				notification.setContet("The user " + notifierUsername + " voted for your comment.");
			else if(notificationType.equals("Post repost"))
				notification.setContet("The user " + notifierUsername + " reposted your post.");
			
			notification.setNotifierUsername(notifierUsername);
			notification.setNotificationTypeId(this.notificationTypeDao.getNotificationTypeByTypeName(notificationType));
			notification.setUserId(this.registeredUseDao.getRegisteredUserByUsername(username));
			notification.setChecked(false);
			
			if(this.notificationDao.createNotification(notification))
				return "Notification was created"; 
		}
			return "Notification was not created"; 
		
	}*/
	public List<Notification> getAllNotificationsByUsername(String username, int pageNumber)	{
		if(!this.registeredUseDao.userExists(username))
			throw new IllegalArgumentException("The user with the specified username does not exist.");
		
		Pageable pageRequest = new PageRequest(pageNumber, 10, Sort.Direction.DESC, "creationMoment"); 
		
		return this.notificationDao.getAllNotificationsByUsername(
				this.registeredUseDao.getRegisteredUserByUsername(username), 
				pageRequest); 
	}
	public List<Notification> getNewNotificationsByUsername(String username, long lastCheckedMillisec, int pageNumber) {
		try {
			Timestamp lastChecked = new Timestamp(lastCheckedMillisec) ;
			Pageable pageRequest = new PageRequest(pageNumber, 10, Sort.Direction.DESC, "creationMoment") ; 
			
			return this.notificationDao.getNewNotificationsByUsername(
					this.registeredUseDao.getRegisteredUserByUsername(username) 
					, lastChecked
					, pageRequest) ;
		} catch (Exception e) {
			throw e ; 
		}
	}
	
	public int getNewNotificationCount(String username, Timestamp lastChecked)	{
		try {
			RegisteredUser userId = this.registeredUseDao.getRegisteredUserByUsername(username) ; 
			return this.notificationDao.getNewNotificationCount(userId, lastChecked) ; 
		} catch (Exception e) {
			throw e ; 
		}
	}
}
