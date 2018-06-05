package com.meminator.demo.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.meminator.demo.models.Comment;
import com.meminator.demo.services.CommentService;
import com.meminator.demo.viewModels.CommentViewModel;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

	CommentService commentService;

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@PreAuthorize("hasrole('ROLE_user')")
	@RequestMapping(value="/postId/{commentId}", method=RequestMethod.GET)
	public ResponseEntity getPostId(OAuth2Authentication authentication
			, @PathVariable("commentId") long commentId)	{
		try {
			long postId ; 
			postId = this.commentService.getPostByCommentId(commentId) ; 
			
			return ResponseEntity.status(HttpStatus.OK).body(postId) ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()) ; 
		}
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity createComment(OAuth2Authentication authentication,
			@RequestBody final CommentInfo commentInfo) {
		try {

			String response = this.commentService.createComment(Long.valueOf(commentInfo.postId).longValue(),
					authentication.getName(), commentInfo.commentText);
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
	}
//<<<<<<< HEAD
	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
	@RequestMapping(value="/{postId}/{pageNumber}", method=RequestMethod.GET)
	public ResponseEntity getAllComments(
			@PathVariable("postId") long postId,
			@PathVariable("pageNumber") int pageNumber)	{
		
		List<Comment> comments = new ArrayList<Comment>(); 
		List<CommentViewModel>commentsVM = new ArrayList<CommentViewModel>();
		try {
			comments = this.commentService.getAllComments(postId, pageNumber);
			
			for(int i = 0; i < comments.size(); i ++)	{
/*=======

	@RequestMapping(value = "/postId={postId}/sortBy={sortBy}/pageNumber={pageNumber}", method = RequestMethod.GET)
	public ResponseEntity getAllComments(@PathVariable("postId") long postId, @PathVariable("sortBy") String sortBy,
			@PathVariable("pageNumber") int pageNumber) {

		List<Comment> comments = new ArrayList<Comment>();
		List<CommentViewModel> commentsVM = new ArrayList<CommentViewModel>();
		try {
			comments = this.commentService.getAllComments(postId, sortBy, pageNumber);

			for (int i = 0; i < comments.size(); i++) {
>>>>>>> 3bd54351e027575865a0d7093e8cc854c0b50a13 */
				commentsVM.add(new CommentViewModel(comments.get(i).getId(),
						comments.get(i).getUserCommenterId().getUsername(), comments.get(i).getComment(),
						comments.get(i).getUpVoteCount(), comments.get(i).getDownVoteCount()));
			}
			return ResponseEntity.status(HttpStatus.OK).body(commentsVM);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
	}

	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity updateComment(OAuth2Authentication authentication,
			@RequestBody final UpdatedCommentInfo info) {
		try {

			String response = this.commentService.editComment(Long.valueOf(info.commentId).longValue(),
					authentication.getName(), info.commentText);
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
		}
	}

	@PreAuthorize("hasRole('ROLE_user')")
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity deleteComment(OAuth2Authentication authentication,
			@RequestBody final UpdatedCommentInfo info) {

		try {
//<<<<<<< HEAD
			/*        this.commentService.deleteComment(info.commenterUsername,
					Long.valueOf(info.commentId).longValue());
			return ResponseEntity.status(HttpStatus.OK).body("The specified comment was deleted.");  */ 
//=======
			this.commentService.deleteComment(authentication.getName(), Long.valueOf(info.commentId).longValue());
			return ResponseEntity.status(HttpStatus.OK).body("The specified comment was deleted.");
//>>>>>>> 3bd54351e027575865a0d7093e8cc854c0b50a13
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(e.getLocalizedMessage());
		}
	}

	private static class CommentInfo {
		public String postId;
		public String commentText;

		public CommentInfo() {
		}

		public String getCommentText() {
			return commentText;
		}

		public void setCommentText(String commentText) {
			this.commentText = commentText;
		}

		public void setPostId(String postId) {
			this.postId = postId;
		}

		public String getPostId() {
			return postId;
		}
	}

	private static class UpdatedCommentInfo {
		public String commentId;
		public String commentText;

		public UpdatedCommentInfo() {
		}

		public String getCommentId() {
			return commentId;
		}

		public void setCommentId(String commentId) {
			this.commentId = commentId;
		}

		public String getCommentText() {
			return commentText;
		}

		public void setCommentText(String commentText) {
			this.commentText = commentText;
		}

	}
}
