package com.meminator.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.demo.models.Comment;
import com.meminator.demo.services.CommentService;
import com.meminator.demo.viewModels.CommentViewModel;

@RestController
@RequestMapping(value="/comments")
public class CommentController {
	
	CommentService commentService; 
	
	@Autowired
	public void setCommentService(CommentService commentService)	{
		this.commentService = commentService; 
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity createComment(@RequestBody final CommentInfo commentInfo)
	{
		try {
			String response = this.commentService.createComment(
					Long.valueOf(commentInfo.postId).longValue()
					, commentInfo.commenterUsername
					, commentInfo.commentText
					);
			return ResponseEntity.status(HttpStatus.OK).body(response); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage()); 
		}
	}
	@RequestMapping(value="/{postId}/{pageNumber}", method=RequestMethod.GET)
	public ResponseEntity getAllComments(
			@PathVariable("postId") long postId,
			@PathVariable("pageNumber") int pageNumber)	{
		
		List<Comment> comments = new ArrayList<Comment>(); 
		List<CommentViewModel>commentsVM = new ArrayList<CommentViewModel>();
		try {
			comments = this.commentService.getAllComments(postId, pageNumber);
			
			for(int i = 0; i < comments.size(); i ++)	{
				commentsVM.add(new CommentViewModel(comments.get(i).getId(),
						comments.get(i).getUserCommenterId().getUsername(),
						comments.get(i).getComment(),
						comments.get(i).getUpVoteCount(),
						comments.get(i).getDownVoteCount()
						));
			}
			return ResponseEntity.status(HttpStatus.OK).body(commentsVM); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage()); 
		}
	}
	@RequestMapping(value="", method=RequestMethod.PUT)
	public ResponseEntity updateComment(@RequestBody final UpdatedCommentInfo info)	{
		try {
			String response = this.commentService.editComment(
					Long.valueOf(info.commentId).longValue(), 
					info.commenterUsername, 
					info.commentText);
			return ResponseEntity.status(HttpStatus.OK).body(response); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage()); 
		}
	}
	@RequestMapping(value="", method=RequestMethod.DELETE)
	public ResponseEntity deleteComment(@RequestBody final UpdatedCommentInfo info)	{
		
		try {
			this.commentService.deleteComment(info.commenterUsername,
					Long.valueOf(info.commentId).longValue());
			return ResponseEntity.status(HttpStatus.OK).body("The specified comment was deleted."); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getLocalizedMessage()); 
		}
	}
	private static class CommentInfo	{
		public String postId;
		public String commenterUsername; 
		public String commentText; 
	}
	private static class UpdatedCommentInfo	{
		public String commentId; 
		public String commenterUsername;
		public String commentText; 
	}
}
