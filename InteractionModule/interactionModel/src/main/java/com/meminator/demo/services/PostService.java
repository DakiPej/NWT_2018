package com.meminator.demo.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.demo.communicators.AsyncSenderService;
import com.meminator.demo.dao.PostDAO;
import com.meminator.demo.dao.RegisteredUserDAO;
import com.meminator.demo.models.Post;
import com.meminator.demo.models.RegisteredUser;

@Service
public class PostService {
	PostDAO postDao; 
	RegisteredUserDAO registeredUserDao; 
	AsyncSenderService asyncSenderService;
	
	@Autowired
	public void setPostDao	(PostDAO postDao)	{
		this.postDao = postDao; 
	}
	@Autowired
	public void setRegisteredUserDao	(RegisteredUserDAO registeredUserDao)	{
		this.registeredUserDao = registeredUserDao; 
	}
	@Autowired
	public void setasyncSenderService	(AsyncSenderService asyncSenderService)	{
		this.asyncSenderService = asyncSenderService; 
	}
	
	public String createPost(long postId, String posterUsername)	{
	//public String createPost(String posterUsername)	{
		Post post = new Post(); 
		
		//post.setPoster(this.registeredUserDao.getRegisteredUserById(posterId));
		RegisteredUser poster = this.registeredUserDao.getRegisteredUserByUsername(posterUsername); 
		if(poster != null )	{
			post.setId(postId);
			post.setPoster(poster);
			post.setDownVoteCount(0);
			post.setUpVoteCount(0);
			
			if(this.postDao.createPost(post))
				return "Post created"; 
			return "Post was not created"; 
		}
		return "An user with the provided username does not exist"; 
	}
	
	public boolean deletePost(long postId)	{
		return this.postDao.deletePost(postId); 
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
		if(this.postDao.createPost(post))	
			this.asyncSenderService.sendPostVote(
							post.getId(), 
							post.getUpVoteCount(), 
							post.getDownVoteCount());
	}
	
	public Post getPostById(long id)	{
		return this.postDao.findPostById(id);
	}
}
