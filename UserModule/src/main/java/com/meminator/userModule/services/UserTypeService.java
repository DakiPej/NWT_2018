package com.meminator.userModule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.userModule.models.UserType;
import com.meminator.userModule.repositories.UserTypeRepository;

@Service
public class UserTypeService {
	@Autowired
	UserTypeRepository userTypeRepository;
	
	public UserType getUserTypeById(Long id) {
		UserType ut = userTypeRepository.findUserTypeById(id);
		System.out.println("servis " + ut);
		return ut;
	}
}
