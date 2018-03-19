package com.meminator.imageModule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
}