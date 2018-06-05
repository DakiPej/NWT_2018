package com.meminator.userModule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meminator.userModule.services.FollowService;

import io.swagger.annotations.Api;

@Controller
@Api(value="registered user")
@RequestMapping
public class FollowController {
	@Autowired
	FollowService followService;
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="/follow/{friend}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity addFriend(OAuth2Authentication authentication, @PathVariable final String friend){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(followService.addFriend(authentication.getName(), friend));
		} catch (IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error.");
		}
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="/unfollow/{friend}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Boolean> removeFriend(OAuth2Authentication authentication, @PathVariable final String friend){
		try {
			if(followService.removeFriend(authentication.getName(), friend))
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}
	
	@RequestMapping(value="/myFriends", method=RequestMethod.POST)
	@ResponseBody
	//public ResponseEntity myFriends(OAuth2Authentication authentication) {
	public ResponseEntity myFriends(@RequestBody final String username) {
		try {
			//return ResponseEntity.status(HttpStatus.OK).body(followService.myFriends(authentication.getName()));
			return ResponseEntity.status(HttpStatus.OK).body(followService.myFriends(username));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error.");
		}
	}

	@RequestMapping(value="/followedBy", method=RequestMethod.POST)
	@ResponseBody
	//public ResponseEntity myFollowers(OAuth2Authentication authentication) {
	public ResponseEntity myFollowers(@RequestBody final String username) {
		try {
			//return ResponseEntity.status(HttpStatus.OK).body(followService.myFollowers(authentication.getName()));
			return ResponseEntity.status(HttpStatus.OK).body(followService.myFollowers(username));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error.");
		}
	}

	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
	@RequestMapping(value="/areFriends/{username}", method=RequestMethod.GET)
	@ResponseBody
	//public ResponseEntity myFollowers(OAuth2Authentication authentication) {
	public ResponseEntity areFriends(OAuth2Authentication authentication, @PathVariable final String username) {
		try {
			//return ResponseEntity.status(HttpStatus.OK).body(followService.myFollowers(authentication.getName()));
			return ResponseEntity.status(HttpStatus.OK).body(followService.areFriends(authentication.getName(), username));
		} catch(IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		} 
		 catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error.");
		}
	}
}
