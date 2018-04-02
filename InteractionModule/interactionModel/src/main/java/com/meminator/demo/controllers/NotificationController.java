package com.meminator.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.models.Notification;
import com.meminator.demo.services.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	NotificationService notificationService; 
	
	@Autowired
	public void setNotificationService(NotificationService notificationService)	{
		this.notificationService = notificationService; 
	}
	
	/*@RequestMapping(value="", method=RequestMethod.POST)
	public String createNotification(@RequestBody final NotificationInfo info)	{
		
		return this.notificationService.createNotification(info.notifierUsername, info.username, info.notificationType);
	}*/
	
	@RequestMapping(value="/username={username}/pageNumber={pageNumber}", method=RequestMethod.GET)
	//(value="/getNotifications/{username}/{pageNumber}", method=RequestMethod.GET)
	public List<Notification> getAllNotifications(@PathVariable("username") String username, @PathVariable("pageNumber") int pageNumber)	{
		List<Notification> notifications = new ArrayList<Notification>();
		
		notifications = this.notificationService.getAllNotificationsByUsername(username, pageNumber);
		return notifications;
		
	}
	
	private static class NotificationInfo	{
		
		public String notifierUsername;
		public String username; 
		public String notificationType; 
		
		
	}
}
