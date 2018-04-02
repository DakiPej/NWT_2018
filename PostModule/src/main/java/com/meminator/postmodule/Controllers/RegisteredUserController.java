package com.meminator.postmodule.Controllers;

import com.meminator.postmodule.Models.RegisteredUser;
import com.meminator.postmodule.Services.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class RegisteredUserController {

    private RegisteredUserService registeredUserService;

    @Autowired
    public void setRegisteredUserService(RegisteredUserService registeredUserService){
        this.registeredUserService = registeredUserService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createUser(@RequestBody RegisteredUser registeredUser){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(registeredUserService.createUser(registeredUser));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public ResponseEntity getUser(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(registeredUserService.getUser(username));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(registeredUserService.deleteUser(username));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

}
