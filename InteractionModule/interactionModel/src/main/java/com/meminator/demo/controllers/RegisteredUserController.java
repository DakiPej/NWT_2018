package com.meminator.demo.controllers;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	
	@RequestMapping(value="/lastTimeChecked/{username}", method=RequestMethod.GET)	
	public ResponseEntity getLastTimeChecked(@PathVariable("username") String username)	{
		try {
			Timestamp lastTimeChecked = this.registeredUserService.getLastTimeChecked(username) ; 

			return ResponseEntity.status(HttpStatus.OK).body(lastTimeChecked.getTime()) ; 
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	@RequestMapping(value="", method=RequestMethod.PUT)
	public void updateLastTimeChecked(@RequestBody final UpdatedRegisteredUser update)	{
		try {
			Timestamp lastTimeCheckedMillisec = new Timestamp(update.lastTimeChecked) ; 
			Date lastTimeChecked = new Date(lastTimeCheckedMillisec.getTime()) ;
			
			this.registeredUserService.setLastTimeChecked(update.username, lastTimeChecked);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static class UpdatedRegisteredUser	{
		public String username ; 
		public long lastTimeChecked ; 
	}
}
