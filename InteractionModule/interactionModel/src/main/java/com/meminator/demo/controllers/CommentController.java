package com.meminator.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String createComment(@RequestBody final commentInfo commentInfo)
	{
		return this.commentService.createComment(
				Long.valueOf(commentInfo.postId).longValue()
				, commentInfo.commenterUsername
				, commentInfo.commentText
				);
	}
	@RequestMapping(value="/postId={postId}/sortBy={sortBy}/pageNumber={pageNumber}", method=RequestMethod.GET)
	public List<CommentViewModel> getAllComments(
			@PathVariable("postId") long postId,
			@PathVariable("sortBy") String sortBy,
			@PathVariable("pageNumber") int pageNumber)	{
		
		List<Comment> comments = new ArrayList<Comment>(); 
		List<CommentViewModel>commentsVM = new ArrayList<CommentViewModel>();
		
		comments = this.commentService.getAllComments(postId, sortBy, pageNumber);
		
		for(int i = 0; i < comments.size(); i ++)	{
			commentsVM.add(new CommentViewModel(comments.get(i).getId(),
					comments.get(i).getUserCommenterId().getUsername(),
					comments.get(i).getComment(),
					comments.get(i).getUpVoteCount(),
					comments.get(i).getDownVoteCount()
					));
		}
		return commentsVM; 
	}
	@RequestMapping(value="/username={username}/commentId={commentId}", method=RequestMethod.DELETE)
	public String deleteComment(@PathVariable("username") String usnername, @PathVariable("commentId") String commentId)	{
		
		return this.commentService.deleteComment(usnername, Long.valueOf(commentId).longValue());
	}
	private static class commentInfo	{
		public String postId;
		public String commenterUsername; 
		public String commentText; 
	}
}
