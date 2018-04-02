package com.meminator.postmodule.Services;

import com.meminator.postmodule.DAO.PostDAO;
import com.meminator.postmodule.DAO.RegisteredUserDAO;
import com.meminator.postmodule.DAO.TagDAO;
import com.meminator.postmodule.Models.Post;
import com.meminator.postmodule.Models.RegisteredUser;
import com.meminator.postmodule.Models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostDAO postDAO;
    private RegisteredUserDAO registeredUserDAO;
    private TagDAO tagDAO;

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

    public Post createPost(Post post, String username){

        if(post.getImageURL().equals("")){
            throw new IllegalArgumentException("Url of an image isn't specified!");
        }

        Optional<RegisteredUser> user = registeredUserDAO.getUser(username);
        if(user.isPresent()){
            post.setUser(user.get());
            return postDAO.savePost(post);

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

    public boolean deletePost(Long id){
        return postDAO.deletePost(id);
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

}
