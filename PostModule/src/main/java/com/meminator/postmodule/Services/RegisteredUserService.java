package com.meminator.postmodule.Services;

import com.meminator.postmodule.DAO.RegisteredUserDAO;
import com.meminator.postmodule.Models.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisteredUserService {

    private RegisteredUserDAO registeredUserDAO;

    @Autowired
    public void setRegisteredUserDAO(RegisteredUserDAO registeredUserDAO){
        this.registeredUserDAO = registeredUserDAO;
    }

    public RegisteredUser createUser(RegisteredUser user){
        return registeredUserDAO.createUser(user);
    }

    public RegisteredUser getUser(String username){

        Optional<RegisteredUser> user = registeredUserDAO.getUser(username);

        if(user.isPresent()){
            return user.get();
        }else{
            throw new IllegalArgumentException("User with given username does not exist!");
        }

    }

    public boolean deleteUser(String username){

        return registeredUserDAO.deleteUser(username);

    }
}
