package com.meminator.demo.controllers;

import java.sql.Timestamp;
import java.util.Date;

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

import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.services.RegisteredUserService;

@RestController
@RequestMapping(value="/users")
public class RegisteredUserController {
	RegisteredUserService registeredUserService ; 
	
	@Autowired
	public void setRegisteredUserService(RegisteredUserService registeredUserService)	{
		this.registeredUserService = registeredUserService ; 
	}
	
	
	@PreAuthorize("hasRole('ROLE_user')") 
	@RequestMapping(value="/lastTimeChecked", method=RequestMethod.GET)	
	public ResponseEntity getLastTimeChecked(OAuth2Authentication authentication)	{
		try {
			Timestamp lastTimeChecked = this.registeredUserService.getLastTimeChecked(authentication.getName()) ; 

			return ResponseEntity.status(HttpStatus.OK).body(lastTimeChecked.getTime()) ; 
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	@PreAuthorize("hasRole('ROLE_user')") 
	@RequestMapping(value="/lastTimeChecked", method=RequestMethod.PUT)
	public ResponseEntity updateLastTimeChecked(OAuth2Authentication authentication
										,@RequestBody final UpdatedRegisteredUser update)	{
		try {
			Timestamp lastTimeCheckedMillisec = new Timestamp(update.lastTimeChecked) ; 
			Date lastTimeChecked = new Date(lastTimeCheckedMillisec.getTime()) ;
			
			this.registeredUserService.setLastTimeChecked(authentication.getName(), lastTimeChecked);
			return ResponseEntity.status(HttpStatus.OK).body("Updated") ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	public static class UpdatedRegisteredUser	{
		public long lastTimeChecked ; 
	}
}
