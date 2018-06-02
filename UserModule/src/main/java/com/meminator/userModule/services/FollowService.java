package com.meminator.userModule.services;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.amqp.AmqpException;
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
	
	public Boolean addFriend(String username, String friendUsername) throws Exception, ServletException, IllegalArgumentException{
		try {
			RegisteredUser user = registeredUserRepository.getByUsername(username);
			RegisteredUser friend = registeredUserRepository.getByUsername(friendUsername);
			
			if(user == null)
				throw new IllegalArgumentException("User does not exist.");
			else if(friend == null)
				throw new IllegalArgumentException("Friend does not exist.");
			else if(user == friend)
				throw new IllegalArgumentException("User and friend are same.");
			else if(followRepository.frendshipExists(username, friendUsername))
				throw new IllegalArgumentException("Friendship already exists.");
			
			Follow follow = new Follow();
			follow.setUser(user);
			follow.setFollowedUser(friend);
			follow = followRepository.save(follow);
			
			FollowMessage followMessage = new FollowMessage(user.getUsername(), friend.getUsername());
			
			rabbitTemplate.convertAndSend("user-queue-exchange","user.follow", followMessage);
			
			return true;
		} catch (IllegalArgumentException e){
			throw e;
		} catch (AmqpException e){
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean removeFriend(String username, String friendUsername) throws Exception, IllegalArgumentException, ServletException{
		try {
			RegisteredUser user = registeredUserRepository.getByUsername(username);
			RegisteredUser friend = registeredUserRepository.getByUsername(friendUsername);
			
			if(user == null)
				throw new IllegalArgumentException("User does not exist.");
			else if(friend == null)
				throw new IllegalArgumentException("Friend does not exist.");
			else if(user == friend)
				throw new IllegalArgumentException("User and friend are same.");
			else if(followRepository.frendshipExists(username, friendUsername))
				throw new IllegalArgumentException("Friendship already exists.");
			
			Follow follow = followRepository.getFriendship(username, friendUsername);
			followRepository.delete(follow);
			
			return true;
		} catch (IllegalArgumentException e){
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<String> myFriends(String username) throws IllegalArgumentException {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(username);
			if(ru == null)
				throw new IllegalArgumentException("User with username " + username + " does not exist.");
			return followRepository.getFriends(ru.getId());
			
		} catch(IllegalArgumentException e){
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<String> myFollowers(String username) throws IllegalArgumentException {
		try {
			RegisteredUser ru = registeredUserRepository.getByUsername(username);
			if(ru == null)
				throw new IllegalArgumentException("User with username " + username + " does not exist.");
			return followRepository.getFollowers(ru.getId());
			
		} catch(IllegalArgumentException e){
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}
