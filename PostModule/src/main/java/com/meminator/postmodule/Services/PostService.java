package com.meminator.postmodule.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meminator.postmodule.DAO.PostDAO;
import com.meminator.postmodule.DAO.RegisteredUserDAO;
import com.meminator.postmodule.DAO.TagDAO;
import com.meminator.postmodule.Models.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostDAO postDAO;
    private RegisteredUserDAO registeredUserDAO;
    private TagDAO tagDAO;
    private AsyncSender asyncSender;
    private RestTemplate restTemplate;

    @Autowired
    public void setPostDAO(PostDAO postDAO){
        this.postDAO = postDAO;
    }

    @Autowired
    public void setRegisteredUserDAO(RegisteredUserDAO registeredUserDAO){
        this.registeredUserDAO = registeredUserDAO;
    }

    @Autowired
    public void setTagDAO(TagDAO tagDAO){
        this.tagDAO = tagDAO;
    }

    @Autowired
    public void setAsyncSender(AsyncSender asyncSender){
        this.asyncSender = asyncSender;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public Post createPost(PostVM postVM, String username){

        if(postVM.getImageURL().equals("")){
            throw new IllegalArgumentException("Url of an image isn't specified!");
        }

        Optional<RegisteredUser> user = registeredUserDAO.getUser(username);
        if(user.isPresent()){
            Post post = new Post();
            post.setUser(user.get());
            post.setInfo(postVM.getInfo());
            post.setImageURL(postVM.getImageURL());
            Optional<Post> repost = postDAO.getPost(postVM.getRepostID());
            post.setRepost(repost.isPresent()?repost.get():null);
            List<Tag> tags = tagDAO.getAllInList(postVM.getTags());
            for(Tag i: tags){
                if(postVM.getTags().contains(i.getName())) postVM.getTags().remove(i.getName());
            }
            for(String i: postVM.getTags()){
                tags.add(tagDAO.createTag(new Tag(i)));
            }
            post.setTags(tags);
            Post newPost =  postDAO.savePost(post);
            if(newPost != null){
                asyncSender.sendPost(new PostVMS(post));
            }
            return newPost;
        }else{
            throw new IllegalArgumentException("User with given username does not exist!");
        }

    }

    public Post getPost(Long id){

        Optional<Post> post = postDAO.getPost(id);

        if(post.isPresent()){
            return post.get();
        }else{
            throw new IllegalArgumentException("Post with given id does not exist!");
        }
    }

    public List<Post> getPosts(){

        return postDAO.getPosts();

    }

    public  List<Post> getPostsByTags(List<Tag> tags){

        return postDAO.getPostsByTags(tags);

    }

    public List<Post> getPostsByUser(String username){

        Optional<RegisteredUser> user = registeredUserDAO.getUser(username);
        if(user.isPresent()){
            return postDAO.getPostsByUser(user.get());
        }else{
            throw new IllegalArgumentException("User with given username does not exist!");
        }

    }

    public Post addTags(Long id, List<Tag> tags){

        Optional<Post> prepost = postDAO.getPost(id);

        if(prepost.isPresent()) {
            Post post = prepost.get();
            for (Tag tag :
                    tags) {
                if (!tagDAO.getByName(tag.getName()).isPresent()) tag = tagDAO.createTag(tag);
                if(!post.getTags().contains(tag)) post.getTags().add(tag);
            }
            return postDAO.savePost(post);
        }else{
            throw new IllegalArgumentException("Post with given id does not exist!");
        }
    }

    public boolean deletePost(Long id, String username){
        Optional<Post> post = postDAO.getPost(id);
        if(post.isPresent())
            if(post.get().getUser().getUsername().equals(username)) {
                asyncSender.deletePost(id);
                return postDAO.deletePost(id);
            }
            else throw new SecurityException("User cannot delete this post!");
        else throw new IllegalArgumentException("Post with given id does not exist!");
    }

    public Post editPost(Long id, Post newpost){

        Optional<Post> prepost = postDAO.getPost(id);

        if(prepost.isPresent()) {
            Post post = prepost.get();
            for (Tag tag :
                    newpost.getTags()) {
                if (!tagDAO.getByName(tag.getName()).isPresent()) tag = tagDAO.createTag(tag);
            }
            post.setInfo(newpost.getInfo());
            post.setTags(newpost.getTags());
            return postDAO.savePost(post);
        }else{
            throw new IllegalArgumentException("Post with given id does not exist!");
        }
    }

    public List<Post> getByFollow(String username) throws Exception {

        JSONObject request = new JSONObject();
        request.put("username", username);
        List<String> response = restTemplate.postForObject(
                "http://userModule/myFriends",
                username,
                List.class
                );
            System.out.println(response.get(1));
            List<RegisteredUser> users = registeredUserDAO.findAllByUsernames(response);
            return postDAO.getPostByFollowers(users);
    }

}
