package com.meminator.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meminator.demo.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{

}
