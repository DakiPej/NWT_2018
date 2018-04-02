package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
