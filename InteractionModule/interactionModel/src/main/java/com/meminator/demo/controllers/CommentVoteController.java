package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.services.CommentVoteService;

@RestController
@RequestMapping(value="/commentVotes")
public class CommentVoteController {
	
	CommentVoteService commentVoteService; 
	
	@Autowired
	public void setCommentVoteService(CommentVoteService commentVoteService)	{
		this.commentVoteService = commentVoteService; 
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String createCommentVote(@RequestBody final CommentVoteInfo commentVoteInfo)	{
		return this.commentVoteService.createCommentVote(commentVoteInfo.commentId, commentVoteInfo.voterUsername, commentVoteInfo.upVote);
	}
	
	private static class CommentVoteInfo {
		public long commentId; 
		public String voterUsername;
		public boolean upVote; 
	}
}
