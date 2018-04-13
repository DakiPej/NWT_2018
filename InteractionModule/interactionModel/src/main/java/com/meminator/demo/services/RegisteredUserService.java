package com.meminator.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.RegisteredUserDAO;

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
}
