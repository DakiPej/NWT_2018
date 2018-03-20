package com.meminator.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meminator.demo.models.CommentVote;

public interface CommentVoteRepository extends CrudRepository<CommentVote, Long>{

}
