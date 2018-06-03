package com.meminator.demo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.models.Notification;
import com.meminator.demo.services.NotificationService;
import com.meminator.demo.services.RegisteredUserService;
import com.meminator.demo.viewModels.NotificationResponse;
import com.meminator.demo.viewModels.NotificationViewModel;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	NotificationService notificationService; 
	RegisteredUserService registeredUserService ; 
	
	@Autowired
	public void setNotificationService(NotificationService notificationService)	{
		this.notificationService = notificationService; 
	}
	@Autowired
	public void setRegisteredUserService(RegisteredUserService registeredUserService)	{
		this.registeredUserService = registeredUserService ; 
	}

	
	@RequestMapping(value="/lastTimeChecked/{username}", method=RequestMethod.GET)
	public ResponseEntity getLastTimeChecked(@PathVariable("username") String username)	{
		Timestamp lastTimeChecked ; 
		
		try {
			lastTimeChecked = this.registeredUserService.getLastTimeChecked(username) ; 
			
			return ResponseEntity.status(HttpStatus.OK).body(lastTimeChecked.getTime()) ; 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	@RequestMapping(value="/{username}/{pageNumber}/{lastTimeChecked}", method=RequestMethod.GET)
	//(value="/getNotifications/{username}/{pageNumber}", method=RequestMethod.GET)
	public ResponseEntity getAllNotifications(
			@PathVariable("username") String username, 
			@PathVariable("pageNumber") int pageNumber, 
			@PathVariable("lastTimeChecked") long lastTimeCheckedMillisec)	{
		
		System.out.println("Request dosao.");
		List<Notification> notifications = new ArrayList<Notification>();
		int notificationCount ; 
		Timestamp lastChecked = new Timestamp (lastTimeCheckedMillisec) ; 
		
		try {
			notificationCount = this.notificationService.getNewNotificationCount(username, lastChecked) ;
			if(notificationCount == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No more notifications to show") ;
			
			System.out.println("TU SMO ................................................");
			notifications = this.notificationService.getAllNotificationsByUsername(username, pageNumber);
			
			System.out.println("DI SMO ................................................." + notifications.size());
			NotificationResponse response = new NotificationResponse(notifications, notificationCount) ;
			
			System.out.println("COUNT MU JE " + response.notificationCount);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			System.out.println("EVO GA IDE GRESKA " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
		
	}
	
	/*@RequestMapping(value="NewNotifications/{username}/{pageNumber}", method=RequestMethod.GET) 
	public ResponseEntity
	*/
	private static class NotificationInfo	{
		
		public String notifierUsername;
		public String username; 
		public String notificationType; 
		
		
	}
}
