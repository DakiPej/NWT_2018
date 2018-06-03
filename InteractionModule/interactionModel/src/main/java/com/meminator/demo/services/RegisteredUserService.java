package com.meminator.demo.services;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.RegisteredUser;

@Service
public class RegisteredUserService {
	
	RegisteredUserDAO registeredUserDao; 
	
	@Autowired
	public void setRegisteredUserDao(RegisteredUserDAO registeredUserDao)	{
		this.registeredUserDao = registeredUserDao;
	}
	
	public String createNewRegisteredUser(String username)	{
		
		if(this.registeredUserDao.createRegisteredUser(username))
			return "New user created"; 
		return "New user was not created"; 
	}
	public boolean deleteUser(String username)	{
		return this.registeredUserDao.deleteUser(username);
	}
	
	public Timestamp getLastTimeChecked(String username) {
		Timestamp lastTimeChecked ; 
		
		try {
			RegisteredUser user = this.registeredUserDao.getRegisteredUserByUsername(username) ; 
			lastTimeChecked = new Timestamp(user.getLastTimeChecked().getTime()) ;
			
			return lastTimeChecked ; 
		} catch (Exception e) {
			throw e ; 
		}
	}
	
	
	public void setLastTimeChecked(String username, Date lastTimeChecked)	{
		try {
			RegisteredUser user = this.registeredUserDao.getRegisteredUserByUsername(username) ;
			user.setLastTimeChecked(lastTimeChecked); 
			
			this.registeredUserDao.updateRegisteredUser(user) ; 
		} catch (Exception e) {
			throw e ; 
		}
	}
}
