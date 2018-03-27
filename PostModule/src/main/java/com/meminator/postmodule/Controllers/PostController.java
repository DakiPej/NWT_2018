package com.meminator.postmodule.Controllers;

import com.meminator.postmodule.Models.Post;
import com.meminator.postmodule.Models.Tag;
import com.meminator.postmodule.Services.PostService;
import com.meminator.postmodule.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService){
        this.postService = postService;
    }

    @RequestMapping(value = "/{user}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createPost(@RequestBody Post post, @PathVariable String username){

        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post, username));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getPosts(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPosts());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getPost(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity getPostsByTag(@RequestBody List<Tag> tags){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByTags(tags));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public ResponseEntity getPostsByUser(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUser(username));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getLocalizedMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
    public ResponseEntity addTags(@PathVariable Long id, @RequestBody List<Tag> tags){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.addTags(id,tags));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity deletePost(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

}