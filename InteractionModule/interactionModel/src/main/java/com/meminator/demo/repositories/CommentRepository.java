package com.meminator.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>{

}
