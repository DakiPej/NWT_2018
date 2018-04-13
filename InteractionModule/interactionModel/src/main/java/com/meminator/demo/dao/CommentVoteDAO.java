package com.meminator.demo.dao;

import org.springframework.stereotype.Repository;

import com.meminator.demo.models.Comment;
import com.meminator.demo.models.CommentVote;
import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.repositories.CommentVoteRepository;

@Repository
public class CommentVoteDAO extends BaseDAO<CommentVote, CommentVoteRepository>{
	
	public boolean createCommentVote(CommentVote commentVote)	{
		try {
			this.repo.save(commentVote);
		} catch (Exception e) {
			return false;
		}
		return true; 
	}
	public CommentVote findByCommentAndVote(Comment comment, RegisteredUser voter)	{
		return this.repo.findByCommentAndVoter(comment, voter);
	}
}
