package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="/postId/{commentVoteId}", method=RequestMethod.GET)
	public ResponseEntity getPostId(OAuth2Authentication authentication
									, @PathVariable("commentVoteId") long commentVoteId)	{
		try {
			long postId ; 
			
			postId = this.commentVoteService.getPostByCommentVoteId(commentVoteId) ; 
			return ResponseEntity.status(HttpStatus.OK).body(postId) ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	// NE RADI KAKO TREBA KADA JE upVote == TRUE ----- updejtuje se 
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity createCommentVote(OAuth2Authentication authentication, @RequestBody final CommentVoteInfo commentVoteInfo)	{
		try {
			String response = this.commentVoteService.createCommentVote(commentVoteInfo.commentId, authentication.getName(), commentVoteInfo.upVote);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
	}
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value="", method=RequestMethod.DELETE)
	public ResponseEntity deleteCommentVote(OAuth2Authentication authentication, @RequestBody final DeleteCommentVoteInfo info)	{
		try {
			String response = this.commentVoteService.deleteCommentVote(info.commentId, authentication.getName() /*info.voterUsername*/) ;
					
			 return ResponseEntity.status(HttpStatus.OK).body(response) ; 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	private static class DeleteCommentVoteInfo	{
		public long commentId ; 
		//public String voterUsername ; 
	}
	private static class CommentVoteInfo {
		public long commentId; 
		public boolean upVote; 

		public CommentVoteInfo(){}

		public void setCommentId(long commentId) {
			this.commentId = commentId;
		}
		public void setUpVote(boolean upVote) {
			this.upVote = upVote;
		}

	}
}
