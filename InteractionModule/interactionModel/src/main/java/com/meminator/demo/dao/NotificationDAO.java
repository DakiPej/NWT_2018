package com.meminator.demo.dao;

import org.springframework.stereotype.Repository;

import com.meminator.demo.models.Notification;
import com.meminator.demo.repositories.NotificationRepository;

@Repository
public class NotificationDAO extends BaseDAO<Notification, NotificationRepository>{
	
	public String createNotification(Notification notification)	{
		try {
			this.repo.save(notification);
			return "Notifikacija spasena"; 
		} catch (Exception e) {
			return "Notifikacija se nije spasila"; 
		} 
	}
}
