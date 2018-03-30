package com.meminator.imageModule.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.Post;


@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>{


}
