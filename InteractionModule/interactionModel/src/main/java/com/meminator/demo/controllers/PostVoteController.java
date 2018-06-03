package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String createPostVote(@RequestBody final PostVoteInfo postVoteInfo)	{
		return this.postVoteService.createPostVote(postVoteInfo.post, postVoteInfo.voter, postVoteInfo.upVote);
	}
	
	@RequestMapping(value="", method=RequestMethod.DELETE)
	public ResponseEntity deletePostVote(@RequestBody final DeletePostVoteInfo info)	{
		try {
			String response = this.postVoteService.deletePostVote(info.postId, info.voterUsername) ;
			
			return ResponseEntity.status(HttpStatus.OK).body(response) ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	private static class DeletePostVoteInfo	{
		public long postId ; 
		public String voterUsername ; 
	}
	
	private static class PostVoteInfo	{
		public long post; 
		public String voter; 
		public boolean upVote; 
	}
}
