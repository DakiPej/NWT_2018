package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.services.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	NotificationService notificationService; 
	
	@Autowired
	public void setNotificationService(NotificationService notificationService)	{
		this.notificationService = notificationService; 
	}
	
	@RequestMapping("/create")
	public String createNotification(@RequestBody final NotificationInfo info)	{
		
		return this.notificationService.createNotification(info.notifierUsername, info.username, info.notificationType);
	}
	
	
	private static class NotificationInfo	{
		
		public String notifierUsername;
		public String username; 
		public String notificationType; 
		
		
	}
}
