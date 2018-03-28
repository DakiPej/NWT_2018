package com.meminator.imageModule.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.RegisteredUser;
import com.meminator.imageModule.repositories.RegisteredUserRepository;

@Repository
public class RegisteredUserDAO extends BaseDAO<RegisteredUser, RegisteredUserRepository >{
	
    public RegisteredUser saveUser(RegisteredUser registeredUser){
        return this.repo.save(registeredUser);
    }
    

    public Optional<RegisteredUser> getUser(String username){
        return this.repo.getRegisteredUserByUsername(username);
    }

    public boolean deleteUser(String username){
        this.repo.deleteByUsername(username);
        return !this.repo.getRegisteredUserByUsername(username).isPresent();
    }

}
