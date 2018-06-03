package com.meminator.demo.viewModels;

public class NotificationViewModel {
	

	public long notificationId ; 
	public long referencedObjectId ; 
	public String notificationText ;
	public String notificationType ; 
	
	public NotificationViewModel(long notificationId, long referencedObjectId, String notificationText, String notificationType)	{
		this.notificationId = notificationId ; 
		this.referencedObjectId = referencedObjectId ; 
		this.notificationText = notificationText ;
		this.notificationType = notificationType ; 
	}
	

}
