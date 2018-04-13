package com.meminator.userModule.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.userModule.models.Follow;
import com.meminator.userModule.models.RegisteredUser;
import com.meminator.userModule.rabbitMQ.FollowMessage;
import com.meminator.userModule.repositories.FollowRepository;
import com.meminator.userModule.repositories.RegisteredUserRepository;

import forms.FollowForms.FriendForm;

@Service
public class FollowService {
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	FollowRepository followRepository;
	
	@Autowired
	RegisteredUserRepository registeredUserRepository;
	
	public boolean addFriend(FriendForm friendForm) throws ServletException{
		try {
			RegisteredUser user = registeredUserRepository.getByUsername(friendForm.getUsername());
			RegisteredUser friend = registeredUserRepository.getByUsername(friendForm.getFriendUsername());
			
			if(user == null)
				throw new ServletException("User does not exist.");
			else if(friend == null)
				throw new ServletException("New friend does not exist.");
			else if(user == friend)
				throw new ServletException("User and friend are same.");
			else if(followRepository.frendshipExists(friendForm.getUsername(), friendForm.getFriendUsername()))
				throw new ServletException("Friendship already exists.");
			
			Follow follow = new Follow();
			follow.setUser(user);
			follow.setFollowedUser(friend);
			follow = followRepository.save(follow);
			
			FollowMessage followMessage = new FollowMessage(user.getUsername(), friend.getUsername());
			
			rabbitTemplate.convertAndSend("user-queue-exchange","user.follow", followMessage);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean removeFriend(FriendForm friendForm) throws ServletException{
		try {
			RegisteredUser user = registeredUserRepository.getByUsername(friendForm.getUsername());
			RegisteredUser friend = registeredUserRepository.getByUsername(friendForm.getFriendUsername());
			
			if(user == null)
				throw new ServletException("User does not exist.");
			else if(friend == null)
				throw new ServletException("New friend does not exist.");
			else if(user == friend)
				throw new ServletException("User and friend are same.");
			else if(!followRepository.frendshipExists(friendForm.getUsername(), friendForm.getFriendUsername()))
				throw new ServletException("Friendship does not exists.");
			
			Follow follow = followRepository.getFriendship(friendForm.getUsername(), friendForm.getFriendUsername());
			followRepository.delete(follow);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public List<String> myFriends(String username) {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(username);
			if(ru == null)
				throw new ServletException("User with username " + username + " does not exist.");
			return followRepository.getFriends(ru.getId());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
