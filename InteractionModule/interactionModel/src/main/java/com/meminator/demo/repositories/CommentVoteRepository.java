package com.meminator.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.Comment;
import com.meminator.demo.models.CommentVote;
import com.meminator.demo.models.RegisteredUser;

public interface CommentVoteRepository extends PagingAndSortingRepository<CommentVote, Long>{

	public CommentVote findByCommentAndVoter(Comment comment, RegisteredUser voter);
}
