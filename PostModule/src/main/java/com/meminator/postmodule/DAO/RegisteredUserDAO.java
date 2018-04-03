package com.meminator.postmodule.DAO;

import com.meminator.postmodule.Models.RegisteredUser;
import com.meminator.postmodule.Repositories.IRegisteredUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RegisteredUserDAO extends BaseDAO<RegisteredUser, IRegisteredUserRepository> {

    public RegisteredUser createUser(RegisteredUser registeredUser){
        return this.repo.save(registeredUser);
    }

    public Optional<RegisteredUser> getUser(String username){
        return this.repo.getRegisteredUserByUsername(username);
    }

    public boolean deleteUser(String username){
        this.repo.deleteByUsername(username);
        return !this.repo.getRegisteredUserByUsername(username).isPresent();
    }

    public List<RegisteredUser> findAllByUsernames(List<String> usernames){
        return this.repo.findAllByUsernameIn(usernames);
    }
}
