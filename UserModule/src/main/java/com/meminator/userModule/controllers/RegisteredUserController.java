package com.meminator.userModule.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meminator.userModule.services.RegisteredUserService;

import forms.RegisteredUser.CreateUserForm;
import forms.RegisteredUser.ResetPasswordForm;
import forms.RegisteredUser.UpdateInfoForm;
import io.swagger.annotations.Api;

@Controller
@RequestMapping(value="/user")
@Api(value="registered user")
public class RegisteredUserController {
	@Autowired
	RegisteredUserService registeredUserService;
	
	@RequestMapping(value = "/getAll", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllUsers() {
		try {
			return ResponseEntity.ok(registeredUserService.getAllUsers());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("An error ocurred.");
		}
	}
	
	@RequestMapping(value = "/getByUsername", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity getUserByUsername(@RequestBody final String username) {
		try {
			return ResponseEntity.ok(registeredUserService.getUserByUsername(username));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("An error ocurred.");
		}
	}
	
	@RequestMapping(value = "/searchByUsername", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity searchUserByUsername(@RequestBody final String username) {
		try {
			return ResponseEntity.ok(registeredUserService.searchUserByUsername(username));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("An error ocurred.");
		}
	}
	
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> createUser(@RequestBody @Valid final CreateUserForm createUserForm) {
		try {
			if (registeredUserService.createUser(createUserForm)) 
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.ok(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
	}
	
	@RequestMapping(value = "/resetPassword", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> resetPasword(@RequestBody @Valid final ResetPasswordForm resetPasswordForm){
		try {
			if(registeredUserService.resetPassword(resetPasswordForm))
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.badRequest().body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
	}
	
	@RequestMapping(value = "/updateInfo", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> updateInfo(@RequestBody @Valid final UpdateInfoForm updateInfoForm){
		try {
			if(registeredUserService.updateInfo(updateInfoForm))
				return ResponseEntity.ok(true);
			else 
				return ResponseEntity.badRequest().body(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
	}
	
	@RequestMapping(value="/updateBirthdate", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> updateBirthdate(@RequestBody @DateTimeFormat(pattern = "yyyy-MM-dd") final Date birthdate){
		System.out.println(birthdate);
		return null;
	}
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	@RequestMapping("/rabbit")
	public void rabbit(){
		rabbitTemplate.convertAndSend("users-queue-exchange","user.create","nova poruka");
	}
}
