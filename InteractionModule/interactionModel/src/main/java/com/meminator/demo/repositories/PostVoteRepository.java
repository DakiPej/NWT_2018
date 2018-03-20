package com.meminator.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meminator.demo.models.PostVote;

public interface PostVoteRepository extends CrudRepository<PostVote, Long>{

}
