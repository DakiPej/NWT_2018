package com.meminator.demo.dao;

import org.springframework.stereotype.Repository;

import com.meminator.demo.models.NotificationType;
import com.meminator.demo.repositories.NotificationTypeRepository;

@Repository
public class NotificationTypeDAO extends BaseDAO <NotificationType, NotificationTypeRepository>{
	
	public NotificationType getNotificationTypeByTypeName(String typeName)	{
		
		NotificationType type = new NotificationType();
		try {
			type = new NotificationType(
					this.repo.findBytypeName(typeName)
					);
		} catch (Exception e) {
			
		}
		return type; 
	}
}
