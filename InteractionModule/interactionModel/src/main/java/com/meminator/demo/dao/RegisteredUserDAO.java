package com.meminator.demo.dao;

import java.util.Date;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.repositories.RegisteredUserRepository;

@Repository
public class RegisteredUserDAO extends BaseDAO<RegisteredUser, RegisteredUserRepository>{

	public boolean createRegisteredUser (String username)	{
		RegisteredUser regUser = new RegisteredUser(); 
		
		regUser.setUsername(username);
		regUser.setLastTimeChecked(new Date());
		
		try {
			this.repo.save(regUser); 
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean updateRegisteredUser (RegisteredUser user)	{
		try {
			this.repo.save(user) ; 
			return true ; 
		} catch (Exception e) {
			throw e ;
		}
	}
	public boolean deleteUser(String username)	{
		try {
			this.repo.deleteByUsername(username);
		} catch (Exception e) {
			return false; 
		}
		return true; 
	}

	public RegisteredUser getRegisteredUserById(long id)	{
		RegisteredUser regUser = new RegisteredUser(); 
		
		try {
			regUser = this.one(id);
		} catch (Exception e) {
			
		}
		return regUser;
	}
	
	public RegisteredUser getRegisteredUserByUsername(String username)	{
		
		RegisteredUser regUser = new RegisteredUser();
		try {
			regUser = this.repo.findByUsername(username);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return regUser;
	}
	
	public boolean userExists(String username)	{
		return this.repo.existsByUsername(username);
	}
}
