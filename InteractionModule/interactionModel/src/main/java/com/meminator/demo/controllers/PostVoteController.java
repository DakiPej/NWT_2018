package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="", method=RequestMethod.POST)
	public String createPostVote(OAuth2Authentication authentication, @RequestBody final PostVoteInfo postVoteInfo)	{
		return this.postVoteService.createPostVote(postVoteInfo.post, authentication.getName(), postVoteInfo.upVote);
	}
	
	private static class PostVoteInfo	{
		public long post; 
		public boolean upVote; 
	
		public PostVoteInfo(){}
		public void setPost(long post) {
			this.post = post;
		}
		public void setUpVote(boolean upVote) {
			this.upVote = upVote;
		}
	}
}
