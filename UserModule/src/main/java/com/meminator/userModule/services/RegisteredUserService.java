package com.meminator.userModule.services;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meminator.userModule.models.RegisteredUser;
import com.meminator.userModule.repositories.RegisteredUserRepository;
import com.meminator.userModule.repositories.UserTypeRepository;

import forms.RegisteredUser.CreateUserForm;
import forms.RegisteredUser.ResetPasswordForm;
import forms.RegisteredUser.UpdateInfoForm;

@Service(value = "userService")
public class RegisteredUserService implements UserDetailsService{
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	RegisteredUserRepository registeredUserRepository;
	
	@Autowired
	UserTypeRepository userTypeRepository;

	public List<RegisteredUser> getAllUsers() {
		try {
			return registeredUserRepository.getAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public RegisteredUser getUserByUsername(String username) throws ServletException{
		try {
			if(username == null || username.isEmpty()) 
				throw new ServletException("Username can not be empty.");
			return registeredUserRepository.getByUsername(username);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<RegisteredUser> searchUserByUsername(String username) throws ServletException{
		try {
			if(username == null || username.isEmpty())
				throw new ServletException("Can not search user by empty username.");
			return registeredUserRepository.searchByUsername(username);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public boolean createUser(CreateUserForm createUserForm) throws ServletException{
		try {
			if(registeredUserRepository.getByUsername(createUserForm.getUsername()) != null)
				throw new ServletException("User already exists.");
			RegisteredUser ru = new RegisteredUser();
			ru.setBirthdate(createUserForm.getBirthdate());
			ru.setEmail(createUserForm.getEmail());
			ru.setFirstName(createUserForm.getFirstName());
			ru.setLastName(createUserForm.getLastName());
			ru.setPassword(createUserForm.getPassword());
			ru.setUsername(createUserForm.getUsername());
			ru.setUserTypeID(userTypeRepository.getType("Regular user"));
			ru = registeredUserRepository.save(ru);
			
			String username = createUserForm.getUsername();
			
			rabbitTemplate.convertAndSend("user-queue-exchange", "user.create", username);
			
			return true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean resetPassword(ResetPasswordForm resetPasswordForm) throws ServletException{
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(resetPasswordForm.getUsername());
			if(ru != null) {
				if(resetPasswordForm.getNewPassword() == null || resetPasswordForm.getNewPassword().isEmpty())
					throw new ServletException("Password property can not be empty.");
				else if(resetPasswordForm.getNewPasswordR() == null || resetPasswordForm.getNewPasswordR().isEmpty())
					throw new ServletException("Password property can not be empty.");
				else if(!resetPasswordForm.getNewPassword().equals(resetPasswordForm.getNewPasswordR()))
					throw new ServletException("Passwords do not match.");
				
				ru.setPassword(resetPasswordForm.getNewPassword());
				return true;
			}
			else 
				return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean updateInfo(UpdateInfoForm updateInfoForm) throws ServletException {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(updateInfoForm.getUsername());
			if(ru != null) {
				ru.setInfo(updateInfoForm.getInfo());
				return true;
			}
			else 
				return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean deleteUser(String username) {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(username);
			if(ru == null) 
				throw new ServletException("User does not exist.");
			
			registeredUserRepository.delete(ru);
			rabbitTemplate.convertAndSend("user-queue-exchange", "user.delete", username);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RegisteredUser user = registeredUserRepository.getByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private List<SimpleGrantedAuthority> getAuthority(RegisteredUser user) {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getUserTypeID().getTypeName()));
	}
}
