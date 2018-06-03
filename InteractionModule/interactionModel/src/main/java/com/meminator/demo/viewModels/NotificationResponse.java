package com.meminator.demo.viewModels;

import java.util.ArrayList;
import java.util.List;

import com.meminator.demo.models.Notification;

public class NotificationResponse {
	public List<NotificationViewModel> notifications ; 
	public int notificationCount ; 
	
	public NotificationResponse(List<Notification> notifications, int notificationCount)	{

		this.notificationCount = notificationCount ;
		this.notifications = new ArrayList<>() ; 
		for(int i = 0 ; i < notifications.size() ; i ++ )	{
			
			Notification notification = notifications.get(i) ; 
			String typeName = notification.getNotificationTypeId().getTypeName() ; 
			String notifier = notification.getNotifierUsername() ; 
			String notificationText = ""; 
			
			if(typeName.equals("Followed"))		notificationText = "The user " + notifier + " started following you.";
			
			else if (typeName.equals("Commented"))	notificationText = "The user " + notifier + " commented on your post."; 
			
			else if (typeName.equals("Post vote"))	notificationText = "The user " + notifier + " voted for your post."; 
			
			else if (typeName.equals("Comment vote"))	notificationText = "The user " + notifier + " voted for your comment."; 
			
			else if (typeName.equals("Post repost"))	notificationText = "The user " + notifier + " reposted your post.";
			
			
			NotificationViewModel model = new NotificationViewModel(
					notification.getId()
					, Long.valueOf(notification.getContet()).longValue()
					, notificationText
					, typeName) ;
			
			this.notifications.add(model) ; 
		}
	}
}
