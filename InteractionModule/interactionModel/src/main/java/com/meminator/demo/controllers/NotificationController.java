package com.meminator.demo.controllers;

import java.sql.Timestamp;
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

//<<<<<<< HEAD
	@PreAuthorize("hasRole('ROLE_user')")
	//@RequestMapping(value="/lastTimeChecked/{username}", method=RequestMethod.GET)
	@RequestMapping(value="/lastTimeChecked", method=RequestMethod.GET)
	public ResponseEntity getLastTimeChecked(OAuth2Authentication authentication/*@PathVariable("username") String username*/)	{
		Timestamp lastTimeChecked ; 
		
		try {
			lastTimeChecked = this.registeredUserService.getLastTimeChecked(authentication.getName()/*username*/) ; 
			
			return ResponseEntity.status(HttpStatus.OK).body(lastTimeChecked.getTime()) ; 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
	//@RequestMapping(value="/{username}/{pageNumber}/{lastTimeChecked}", method=RequestMethod.GET)
	@RequestMapping(value="/{pageNumber}/{lastTimeChecked}", method=RequestMethod.GET)
//=======
//	@RequestMapping(value="/username={username}/pageNumber={pageNumber}", method=RequestMethod.GET)
//>>>>>>> 3bd54351e027575865a0d7093e8cc854c0b50a13
	//(value="/getNotifications/{username}/{pageNumber}", method=RequestMethod.GET)
	public ResponseEntity getAllNotifications(OAuth2Authentication authentication,
			//@PathVariable("username") String username, 
			@PathVariable("pageNumber") int pageNumber, 
			@PathVariable("lastTimeChecked") long lastTimeCheckedMillisec)	{
		
		System.out.println("Request dosao.");
		List<Notification> notifications = new ArrayList<Notification>();
		int notificationCount ; 
		Timestamp lastChecked = new Timestamp (lastTimeCheckedMillisec) ; 
		
		try {
//<<<<<<< HEAD
			notificationCount = this.notificationService.getNewNotificationCount(authentication.getName(), lastChecked) ;
			if(notificationCount == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No more notifications to show") ;
			
			System.out.println("TU SMO ................................................");
//			notifications = this.notificationService.getAllNotificationsByUsername(username, pageNumber);
//=======
			notifications = this.notificationService.getAllNotificationsByUsername(authentication.getName(), pageNumber);
//>>>>>>> 3bd54351e027575865a0d7093e8cc854c0b50a13
			
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
