package com.meminator.userModule.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meminator.userModule.services.RegisteredUserService;

import forms.RegisteredUser.CreateUserForm;
import forms.RegisteredUser.ResetPasswordForm;
import forms.RegisteredUser.UpdateInfoForm;
import io.swagger.annotations.Api;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping(value="/users")
@Api(value="registered user")
public class RegisteredUserController {
	@Autowired
	RegisteredUserService registeredUserService;
	
	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
	@RequestMapping(value = "/{username}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getUserByUsername(@PathVariable final String username) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registeredUserService.getUserByUsername(username));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error ocurred.");
		}
	}
	
	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
	@RequestMapping(value = "/search/{username}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity searchUserByUsername(@PathVariable final String username) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(registeredUserService.searchUserByUsername(username));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error ocurred.");
		}
	}
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> createUser(@RequestBody @Valid final CreateUserForm createUserForm) {
		try {
			if (registeredUserService.createUser(createUserForm)) 
				return ResponseEntity.status(HttpStatus.OK).body(true);
			else 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value = "/resetPassword", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Boolean> resetPasword(OAuth2Authentication authentication, @RequestBody @Valid final ResetPasswordForm resetPasswordForm){
		try {
			if(registeredUserService.resetPassword(authentication.getName(), resetPasswordForm))
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.badRequest().body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value = "/updateInfo", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Boolean> updateInfo(OAuth2Authentication authentication, @RequestBody @Valid final UpdateInfoForm updateInfoForm){
		try {
			if(registeredUserService.updateInfo(authentication.getName(), updateInfoForm))
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.badRequest().body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
	}

	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity deleteUser(OAuth2Authentication authentication, @RequestBody final String password){
		try {
			if(registeredUserService.deleteUser(authentication.getName()))
				return ResponseEntity.status(HttpStatus.OK).body(true);
			else 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	/*
	@RequestMapping(value="/updateBirthdate", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> updateBirthdate(@RequestBody final UpdateBirthdateForm updateBirthdateForm){
		System.out.println(updateBirthdateForm.getDate());
		return null;
	}

	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
	@PostMapping("/testt")
	@ResponseBody
	public void testt(){
		System.out.println("TEST");
	}

	@PreAuthorize("hasRole('ROLE_user')")
	@PostMapping("/test")
	@ResponseBody
	public void test(OAuth2Authentication authentication){
		System.out.println(authentication.getName());
	}

	@Autowired
	RabbitTemplate rabbitTemplate;

	@RequestMapping("/rabbit")
	public void rabbit(){
		System.err.println("Rabbit");
		//FollowMessage followMessage = new FollowMessage("daki", "pej");
		//rabbitTemplate.convertAndSend("user-queue-exchange", "user.follow", followMessage);
		rabbitTemplate.convertAndSend("user-queue-exchange", "user.create", "Irfan");
	}
	*/
}
