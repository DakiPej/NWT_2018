package com.meminator.demo.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.NotificationDAO;
import com.meminator.demo.dao.NotificationTypeDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.Notification;
import com.meminator.demo.models.NotificationType;
import com.meminator.demo.models.RegisteredUser;

@Service("NotificationService")
public class NotificationService {
	
	NotificationDAO notificationDao; 
	NotificationTypeDAO notificationTypeDao; 
	RegisteredUserDAO registeredUseDao; 
	
	
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
	
	public String createNotification(String notifierUsername, String username, String notificationType)	{
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
		
	}
	
	public List<Notification> getAllNotificationsByUsername(String username, int pageNumber)	{
		Pageable pageRequest = new PageRequest(pageNumber, 10, Sort.Direction.DESC, "creationMoment"); 
		System.out.println("dosao ovdje...");
		return this.notificationDao.getAllNotificationsByUsername(
				this.registeredUseDao.getRegisteredUserByUsername(username), 
				pageRequest);
	}
}
