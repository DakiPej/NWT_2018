package com.meminator.imageModule.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.Post;
import com.meminator.imageModule.repositories.PostRepository;



@Repository 
public class PostDAO extends BaseDAO<Post, PostRepository > {

	 public Post savePost(Post post){
	        return this.repo.save(post);
	    }
	   
	  public Optional<Post> getPost(Long id){
	        return this.repo.findById(id);
	    }

	    public boolean deletePost(Long id){
	        this.repo.deleteById(id);
	        return !this.repo.findById(id).isPresent();
	    }

}
