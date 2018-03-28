package com.meminator.userModule.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meminator.userModule.services.FollowService;

import forms.FollowForms.FriendForm;
import io.swagger.annotations.Api;

@Controller
@Api(value="registered user")
@RequestMapping(value="/follow")
public class FollowController {
	@Autowired
	FollowService followService;
	
	@RequestMapping(value="/addFriend", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> addFriend(@RequestBody @Valid final FriendForm friendForm){
		try {
			if(followService.addFriend(friendForm))
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.badRequest().body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
	}
	
	@RequestMapping(value="/removeFriend", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> removeFriend(@RequestBody @Valid final FriendForm friendForm){
		try {
			if(followService.removeFriend(friendForm))
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.badRequest().body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
	}
	
	@RequestMapping(value="/myFriends", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity myFriends(@RequestBody final String username) {
		try {
			return ResponseEntity.ok(followService.myFriends(username));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("Error ocurred.");
		}
	}
}
