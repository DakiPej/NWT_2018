package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.services.PostService;

@RestController 
@RequestMapping("/posts")
public class PostController {
	PostService postServcie; 
	
	@Autowired
	public void setPostService(PostService postService)	{
		this.postServcie = postService;
	}
	
	/*@RequestMapping(value="/create")
	public String createPost(@RequestBody final PostInfo postInfo)	{
		
		System.out.println("PostInfo.id = " + postInfo.id + "postInfo.userPosterId = " + postInfo.userPosterId);
		return this.postServcie.createPost((long) postInfo.id, (long) postInfo.userPosterId);
	}
	
	private static class PostInfo	{
		public long id; 
		public long userPosterId; 
	}
	*/
}
