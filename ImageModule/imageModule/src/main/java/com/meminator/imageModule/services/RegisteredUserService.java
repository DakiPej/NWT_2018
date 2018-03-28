package com.meminator.imageModule.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.imageModule.dao.RegisteredUserDAO;
import com.meminator.imageModule.models.RegisteredUser;

@Service
public class RegisteredUserService {

	private RegisteredUserDAO registeredUserDAO;

    @Autowired
    public void setRegisteredUserDAO(RegisteredUserDAO registeredUserDAO){
        this.registeredUserDAO = registeredUserDAO;
    }

    public RegisteredUser createUser(RegisteredUser user){
        return registeredUserDAO.saveUser(user);
    }

    public RegisteredUser getUser(String username){

        Optional<RegisteredUser> user = registeredUserDAO.getUser(username);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new IllegalArgumentException("User with given username does not exist!");
        }

    }
    @Transactional 
    public boolean deleteUser(String username){
    	Optional<RegisteredUser> user = registeredUserDAO.getUser(username);

        if(user.isPresent()){
        	return registeredUserDAO.deleteUser(username);
        }else{
            throw new IllegalArgumentException("User with given username does not exist!");
        }
        
    }

}
