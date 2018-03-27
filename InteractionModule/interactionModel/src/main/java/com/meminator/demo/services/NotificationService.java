package com.meminator.demo.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	private boolean validatateNotification(Notification notification)	{
		return true; 
		
	}
	
	public String createNotification(String notifierUsername, String username, String notificationType)	{
		Notification notification = new Notification(); 
		
		
		notification.setCreationMoment(new Date());
		notification.setContet("");
		notification.setNotificationTypeId(this.notificationTypeDao.getNotificationTypeByTypeName(notificationType));
		notification.setUserId(this.registeredUseDao.getRegisteredUserByUsername(notifierUsername));
		notification.setChecked(false);
		
		return this.notificationDao.createNotification(notification);
	}
}
