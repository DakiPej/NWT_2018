package com.meminator.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Long>{
	
}
