package com.meminator.postmodule.Controllers;

import com.meminator.postmodule.Models.Tag;
import com.meminator.postmodule.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping(path = "/tags")
public class TagController {

    private TagService tagService;

    @Autowired
    private void setTagService(TagService tagService){
        this.tagService = tagService;
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createTag(@RequestBody Tag tag){

        try{

            return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(tag));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }

    }

    @PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getTags(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tagService.getTags());
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getTag(@PathVariable Long id){

        try{
            return ResponseEntity.status(HttpStatus.OK).body(tagService.getTag(id));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }

    }

    @PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET, params = "name")
    public ResponseEntity getTagByName(@RequestParam String name){

        try{
            return ResponseEntity.status(HttpStatus.OK).body(tagService.getTagByName(name));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }

    }

}