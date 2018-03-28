package com.meminator.demo.dao;

import org.springframework.stereotype.Repository;

import com.meminator.demo.models.Post;
import com.meminator.demo.repositories.PostRepository;

@Repository
public class PostDAO extends BaseDAO<Post, PostRepository>{
	public boolean createPost(Post post)	{
		try {
			this.repo.save(post);
		} catch (Exception e) {
			return false; 
		}
		return true; 
	}
	
	public Post findPostById(long id)	{
		Post post = new Post(); 
		try {
			post = this.one(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return post; 
	}
}
