package com.meminator.postmodule.Controllers;

import com.meminator.postmodule.Models.Post;
import com.meminator.postmodule.Models.PostVM;
import com.meminator.postmodule.Models.Tag;
import com.meminator.postmodule.Services.PostService;
import com.meminator.postmodule.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService){
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createPost(OAuth2Authentication authentication, @RequestBody PostVM post){

        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post, authentication.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }

    }
	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getPosts(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPosts());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
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

    @PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(value = "/tags", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity getPostsByTag(@RequestBody Tag[] tags){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByTags(Arrays.asList(tags)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    public ResponseEntity getPostsByUser(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUser(username));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(404).body(e.getLocalizedMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_user')")
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

    @PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity deletePost(OAuth2Authentication authentication, @PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id, authentication.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET, value = "/followfeed")
    public ResponseEntity getPostsByFollow(OAuth2Authentication authentication){
        try{
            return  ResponseEntity.status(HttpStatus.OK).body(postService.getByFollow(authentication.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

}