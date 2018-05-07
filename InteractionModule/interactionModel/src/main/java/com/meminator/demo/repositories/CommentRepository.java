package com.meminator.demo.repositories;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.Comment;
import com.meminator.demo.models.Post;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>{
	
	public List<Comment> findByPostId(Post postId, Pageable pageRequest);
}
