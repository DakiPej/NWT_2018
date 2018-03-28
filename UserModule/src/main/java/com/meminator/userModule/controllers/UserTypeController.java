package com.meminator.userModule.controllers;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meminator.userModule.models.UserType;
import com.meminator.userModule.services.UserTypeService;

@Controller
@RequestMapping(value="/userType")
public class UserTypeController {
	@Autowired
	UserTypeService userTypeService;
	
	@RequestMapping(value="/hello")
	@ResponseBody
	public ResponseEntity hello() throws ServletException{
		try {
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return ResponseEntity.ok("hello");
	}
	
	@RequestMapping(value="/getById", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity getUserTypeById(@RequestBody final Long id) {
		try {
			UserType ut = userTypeService.getUserTypeById(Long.valueOf(id));
			return ResponseEntity.ok(ut);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("Unespected error ocurred.");
		}
		//return ResponseEntity.ok("");
	}
}
