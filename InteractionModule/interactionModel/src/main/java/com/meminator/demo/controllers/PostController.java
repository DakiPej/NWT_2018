package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.services.PostService;

@RestController 
@RequestMapping("/posts/")
public class PostController {
	PostService postServcie; 
	
	@Autowired
	public void setPostService(PostService postService)	{
		this.postServcie = postService;
	}
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String createPost(@RequestBody final PostInfo postInfo)	{
		
		return this.postServcie.createPost(postInfo.id, postInfo.userPosterId);
	}
	
	private static class PostInfo	{
		long id; 
		long userPosterId; 
	}
}
