package com.meminator.demo.dao;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.meminator.demo.models.Comment;
import com.meminator.demo.models.Post;
import com.meminator.demo.repositories.CommentRepository;

@Repository
public class CommentDAO extends BaseDAO<Comment, CommentRepository>{
	
	public boolean createComment(Comment comment)	{
		try {
			this.repo.save(comment); 
		} catch (Exception e) {
			return false; 
		}
		return true; 
	}
	public Comment findCommentById(long id)	{
		Comment comment = new Comment(); 
		try {
			comment = this.one(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return comment; 
	}
	public void deleteComment(Comment comment)	{
		try {
			this.repo.delete(comment);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public List<Comment> getAllCommentsByPostId(Post postId, Pageable pageRequest)	{
		
		List<Comment> comments = new ArrayList<Comment>(); 
		comments = this.repo.findByPostId(postId, pageRequest);
		
		return comments; 
	}
	public boolean exists (long id)	{
		return this.exists(id); 
	}
}
