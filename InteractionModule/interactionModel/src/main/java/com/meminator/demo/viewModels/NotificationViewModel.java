package com.meminator.demo.viewModels;

public class NotificationViewModel {
	
	public NotificationViewModel(long notificationId, long referencedObjectId, String notificationText)	{
		this.notificationId = notificationId; 
		this.referencedObjectId = referencedObjectId; 
		this.notificationText = notificationText; 
	}
	
	public long notificationId; 
	public long referencedObjectId; 
	public String notificationText;
}
