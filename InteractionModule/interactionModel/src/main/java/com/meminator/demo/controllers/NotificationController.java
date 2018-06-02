package com.meminator.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.models.Notification;
import com.meminator.demo.services.NotificationService;
import com.meminator.demo.viewModels.NotificationViewModel;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	NotificationService notificationService; 
	
	@Autowired
	public void setNotificationService(NotificationService notificationService)	{
		this.notificationService = notificationService; 
	}

	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="/username={username}/pageNumber={pageNumber}", method=RequestMethod.GET)
	//(value="/getNotifications/{username}/{pageNumber}", method=RequestMethod.GET)
	public ResponseEntity getAllNotifications(OAuth2Authentication authentication,
			@PathVariable("username") String username, 
			@PathVariable("pageNumber") int pageNumber)	{
		
		List<Notification> notifications = new ArrayList<Notification>();
		List<NotificationViewModel> notificationsVM = new ArrayList<NotificationViewModel>(); 
		try {
			notifications = this.notificationService.getAllNotificationsByUsername(authentication.getName(), pageNumber);
			
			for(int i = 0; i < notifications.size(); i ++)	{
				String typeName = notifications.get(i).getNotificationTypeId().getTypeName();
				String notifier = notifications.get(i).getNotifierUsername(); 
				NotificationViewModel notification = new NotificationViewModel(
						notifications.get(i).getId(),
						Long.valueOf(notifications.get(i).getContet()).longValue(),
						""); 
				
				if(typeName.equals("Followed"))
					notification.notificationText = "The user " + notifier + " started following you."; 
				else if (typeName.equals("Commented"))
					notification.notificationText = "The user " + notifier + " commented on your post."; 
				else if (typeName.equals("Post vote"))
					notification.notificationText = "The user " + notifier + " voted for your post."; 
				else if (typeName.equals("Comment vote"))
					notification.notificationText = "The user " + notifier + " voted for your comment."; 
				else if (typeName.equals("Post repost"))
					notification.notificationText = "The user " + notifier + " reposted your post."; 
				notificationsVM.add(notification);
			}
			if(notificationsVM.size() == 0)
				return ResponseEntity.status(HttpStatus.OK).body("No more notifications to show.");
			return ResponseEntity.status(HttpStatus.OK).body(notificationsVM);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
		
	}
	
	private static class NotificationInfo	{
		
		public String notifierUsername; 
		public String notificationType; 
		
		public NotificationInfo(){}

		public void setNotificationType(String notificationType) {
			this.notificationType = notificationType;
		}
		public void setNotifierUsername(String notifierUsername) {
			this.notifierUsername = notifierUsername;
		}
	}
}
