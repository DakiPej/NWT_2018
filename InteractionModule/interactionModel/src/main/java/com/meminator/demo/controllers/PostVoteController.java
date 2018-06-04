package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		try {
			return this.postVoteService.createPostVote(postVoteInfo.post, authentication.getName(), postVoteInfo.upVote);
		} catch (Exception e) {
			return e.getMessage() ;
		}
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="", method=RequestMethod.DELETE)
	public ResponseEntity deletePostVote(OAuth2Authentication authentication,
			@RequestBody final DeletePostVoteInfo info)	{
		try {
			String response = this.postVoteService.deletePostVote(info.postId, authentication.getName() /*info.voterUsername*/) ;
			
			return ResponseEntity.status(HttpStatus.OK).body(response) ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	private static class DeletePostVoteInfo	{
		public long postId ; 
		//public String voterUsername ; 
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
