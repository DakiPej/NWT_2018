package com.meminator.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meminator.demo.models.Post;

public interface PostRepository extends CrudRepository<Post, Long>{

}
