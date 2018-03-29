package com.meminator.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.demo.dao.PostDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.Post;

@Service
public class PostService {
	PostDAO postDao; 
	RegisteredUserDAO registeredUserDao; 
	
	@Autowired
	public void setPostDao	(PostDAO postDao)	{
		this.postDao = postDao; 
	}
	@Autowired
	public void setRegisteredUserDao	(RegisteredUserDAO registeredUserDao)	{
		this.registeredUserDao = registeredUserDao; 
	}
	
	public String createPost(long id, long posterId)	{
		Post post = new Post(); 
		
		System.out.println("ID = " + id + " POSTED ID = " + posterId);
		post.setId(id);
		post.setRegUserId(this.registeredUserDao.getRegisteredUserById(posterId));
		System.out.println("USER SA ID-em " + posterId + " ima username " + this.registeredUserDao.getRegisteredUserById(posterId).getUsername());
		post.setDownVoteCount(0);
		post.setUpVoteCount(0);
		System.out.println("Post id = " + post.getId() + " poster id = " + post.getRegUserId().getId() + " down vote count = " + post.getDownVoteCount() + " up vote count = " + post.getUpVoteCount());
		if(this.postDao.createPost(post))
			return "Post created"; 
		return "Post was not created"; 
	}
	
	public Post getPostById(long id)	{
		return this.postDao.findPostById(id);
	}
}
