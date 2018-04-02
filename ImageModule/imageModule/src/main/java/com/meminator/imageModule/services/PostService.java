package com.meminator.imageModule.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.imageModule.dao.PostDAO;
import com.meminator.imageModule.models.Post;


@Service
public class PostService {

	private PostDAO postDAO;
    
	@Autowired
    public void setPostDAO(PostDAO postDAO){
        this.postDAO = postDAO;
    }
	
	 public Post createPost(Post post){
	        return postDAO.savePost(post);
	    }

	    public Post getPost(Long id){

	        Optional<Post> post = postDAO.getPost(id);

	        if(post.isPresent()){
	            return post.get();
	        }else{
	            throw new IllegalArgumentException("Post with given id does not exist!");
	        }

	    }
	    @Transactional 
	    public boolean deletePost(Long id){
	    	Optional<Post> post = postDAO.getPost(id);

	        if(post.isPresent()){
	        	return postDAO.deletePost(id);
	        }else{
	            throw new IllegalArgumentException("Post with given id does not exist!");
	        }
	        
	    }

}
