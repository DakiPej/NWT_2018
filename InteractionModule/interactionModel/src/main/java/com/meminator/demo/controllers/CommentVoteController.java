package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity createCommentVote(@RequestBody final CommentVoteInfo commentVoteInfo)	{
		try {
			String response = this.commentVoteService.createCommentVote(commentVoteInfo.commentId, commentVoteInfo.voterUsername, commentVoteInfo.upVote);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
	}
	@RequestMapping(value="", method=RequestMethod.DELETE)
	public ResponseEntity deleteCommentVote(@RequestBody final DeleteCommentVoteInfo info)	{
		try {
			String response = this.commentVoteService.deleteCommentVote(info.commentId, info.voterUsername) ;
					
			 return ResponseEntity.status(HttpStatus.OK).body(response) ; 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	private static class DeleteCommentVoteInfo	{
		public long commentId ; 
		public String voterUsername ; 
	}
	private static class CommentVoteInfo {
		public long commentId; 
		public String voterUsername;
		public boolean upVote; 
	}
}
