package com.meminator.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.CommentVote;

public interface CommentVoteRepository extends PagingAndSortingRepository<CommentVote, Long>{

}
