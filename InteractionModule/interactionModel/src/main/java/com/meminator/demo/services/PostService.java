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
		
		post.setId(id);
		post.setPoster(this.registeredUserDao.getRegisteredUserById(posterId));
		post.setDownVoteCount(0);
		post.setUpVoteCount(0);
		
		if(this.postDao.createPost(post))
			return "Post created"; 
		return "Post was not created"; 
	}
	
	public void updateVoteCount(long postId, boolean upVote, boolean doesntExist)	{
		Post post = this.postDao.findPostById(postId);
		if(doesntExist)	
			if (upVote)
				post.setUpVoteCount(post.getUpVoteCount() + 1);
			else
				post.setDownVoteCount(post.getDownVoteCount() + 1);
		else
			if(upVote)	{
				post.setUpVoteCount(post.getUpVoteCount() + 1);
				post.setDownVoteCount(post.getDownVoteCount() - 1);
			}
			else {
				post.setUpVoteCount(post.getUpVoteCount() - 1);
				post.setDownVoteCount(post.getDownVoteCount() + 1);
			}
		this.postDao.createPost(post);
	}
	public Post getPostById(long id)	{
		return this.postDao.findPostById(id);
	}
}
