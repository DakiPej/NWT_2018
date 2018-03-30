package com.meminator.imageModule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.imageModule.models.Post;
import com.meminator.imageModule.services.PostService;


@RestController
@RequestMapping(path = "/posts")
public class PostController {

	  private PostService postService;

	    @Autowired
	    public void setPostService(PostService postService){
	        this.postService = postService;
	    }

	    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	    public ResponseEntity createUser(@RequestBody Post post){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(postService.createPost(post));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
	        }
	    }

	    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
	    public ResponseEntity getUser(@PathVariable Long id){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	        }catch (IllegalArgumentException e){
	            return ResponseEntity.status(404).body(e.getLocalizedMessage());
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
	        }
	    }
	    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	    public ResponseEntity deleteUser(@PathVariable Long id){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
	        }
	    }
}
