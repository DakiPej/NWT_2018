package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.services.PostVoteService;

@RestController
@RequestMapping("/postVotes")
public class PostVoteController {
	
	PostVoteService postVoteService; 
	
	@Autowired
	public void setPostVoteService(PostVoteService postVoteService)	{
		this.postVoteService = postVoteService; 
	}
	
	@RequestMapping("/create")
	public String createPostVote(@RequestBody final PostVoteInfo postVoteInfo)	{
		return this.postVoteService.createPostVote(postVoteInfo.post, postVoteInfo.voter, postVoteInfo.upVote);
	}
	
	private static class PostVoteInfo	{
		public long post; 
		public String voter; 
		public boolean upVote; 
	}
}
