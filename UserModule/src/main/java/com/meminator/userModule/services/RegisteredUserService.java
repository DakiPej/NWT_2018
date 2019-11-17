package com.meminator.userModule.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meminator.userModule.models.RegisteredUser;
import com.meminator.userModule.repositories.FollowRepository;
import com.meminator.userModule.repositories.RegisteredUserRepository;
import com.meminator.userModule.repositories.UserTypeRepository;

import forms.RegisteredUser.CreateUserForm;
import forms.RegisteredUser.ResetPasswordForm;
import forms.RegisteredUser.UpdateInfoForm;
import viewmodels.UserViewModel;

@Service(value = "userService")
public class RegisteredUserService implements UserDetailsService {
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	RegisteredUserRepository registeredUserRepository;

	@Autowired
	UserTypeRepository userTypeRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	FollowRepository followRepository;

	public UserViewModel getUserByUsername(String username) throws Exception, IllegalArgumentException {
		try {
			if (username == null || username.isEmpty())
				throw new IllegalArgumentException("Username cannot be empty.");

			RegisteredUser ru = registeredUserRepository.getByUsername(username);

			if (ru == null)
				throw new IllegalArgumentException("User does not exist.");

			List<String> following = followRepository.getFriends(ru.getId());
			List<String> followedBy = followRepository.getFollowers(ru.getId());
			UserViewModel uvm = new UserViewModel(ru.getFirstName(), ru.getLastName(), ru.getUsername(), ru.getEmail(),
					ru.getInfo(), following.size(), followedBy.size());

			return uvm;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<String> searchUserByUsername(String username) throws Exception, IllegalArgumentException {
		try {
			if (username == null || username.isEmpty())
				throw new IllegalArgumentException("Cannot search user by empty username.");
			List<String> ru = registeredUserRepository.searchByUsername(username);

			return ru;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean createUser(CreateUserForm createUserForm) throws IllegalArgumentException {
		try {
			if (registeredUserRepository.getByUsername(createUserForm.getUsername()) != null)
				throw new IllegalArgumentException("User already exists.");

			System.out.println(registeredUserRepository.getByUsername(createUserForm.getUsername()));

			RegisteredUser ru = new RegisteredUser();
			ru.setBirthdate(new Date());
			ru.setEmail(createUserForm.getEmail());
			ru.setFirstName(createUserForm.getFirstName());
			ru.setLastName(createUserForm.getLastName());
			ru.setPassword(passwordEncoder.encode(createUserForm.getPassword()));
			ru.setUsername(createUserForm.getUsername());
			ru.setUserTypeID(userTypeRepository.getType("user"));
			ru = registeredUserRepository.save(ru);

			String username = createUserForm.getUsername();
			rabbitTemplate.convertAndSend("user-queue-exchange", "user.create", username);

			return true;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (AmqpException e) {
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean resetPassword(String username, ResetPasswordForm resetPasswordForm) throws IllegalArgumentException {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(username);
			if (ru != null) {

				if (resetPasswordForm.getOldPassword() == null || resetPasswordForm.getOldPassword().isEmpty())
					throw new IllegalArgumentException("Password property cannot be empty.");
				else if (resetPasswordForm.getNewPassword() == null || resetPasswordForm.getNewPassword().isEmpty())
					throw new IllegalArgumentException("Password property cannot be empty.");
				else if (resetPasswordForm.getNewPasswordR() == null || resetPasswordForm.getNewPasswordR().isEmpty())
					throw new IllegalArgumentException("Password property cannot be empty.");
				else if (!resetPasswordForm.getNewPassword().equals(resetPasswordForm.getNewPasswordR()))
					throw new IllegalArgumentException("Passwords do not match.");
				else if (!passwordEncoder.matches(resetPasswordForm.getOldPassword(), ru.getPassword()))
					throw new IllegalArgumentException("Incorrect old password.");

				ru.setPassword(passwordEncoder.encode(resetPasswordForm.getNewPassword()));

				ru = registeredUserRepository.save(ru);
				return true;
			} else
				throw new IllegalArgumentException("User doesn't exist.");
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean updateInfo(String username, UpdateInfoForm updateInfoForm) throws IllegalArgumentException {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(username);
			if (ru != null) {
				ru.setInfo(updateInfoForm.getInfo());
				ru.setFirstName(updateInfoForm.getFirstName());
				ru.setEmail(updateInfoForm.getEmail());
				ru.setLastName(updateInfoForm.getLastName());

				ru = registeredUserRepository.save(ru);
				return true;
			} else
				throw new IllegalArgumentException("User doesn't exist.");
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean deleteUser(String username) throws IllegalArgumentException {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(username);
			if (ru == null)
				throw new IllegalArgumentException("User does not exist.");

			registeredUserRepository.delete(ru);
			rabbitTemplate.convertAndSend("user-queue-exchange", "user.delete", username);

			return true;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (AmqpException e) {
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(
				username + "<----------------------------------------------------------------------------------");
		RegisteredUser user = registeredUserRepository.getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user));
	}

	private List<SimpleGrantedAuthority> getAuthority(RegisteredUser user) {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getUserTypeID().getTypeName()));
	}

}
