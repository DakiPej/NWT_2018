package com.meminator.demo.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.meminator.demo.models.Notification;
import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.repositories.NotificationRepository;

@Repository
public class NotificationDAO extends BaseDAO<Notification, NotificationRepository>{
	
	public boolean createNotification(Notification notification)	{
		try {
			this.repo.save(notification);
		} catch (Exception e) {
			return false;
		}
		return true; 
	}
	public List<Notification> getAllNotificationsByUsername (RegisteredUser userId, Pageable pageRequest)	{
		
		List<Notification> notifications = new ArrayList<Notification>();
		notifications = this.repo.findByUserId(userId, pageRequest);
		return notifications; 
		
	}
}
