package com.meminator.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.meminator.demo.models.Post;
import com.meminator.demo.models.PostVote;
import com.meminator.demo.models.RegisteredUser;

public interface PostVoteRepository extends PagingAndSortingRepository<PostVote, Long>{
	
	@Query("select pv from PostVote pv where pv.post = :post and pv.voter = :voter")
	public PostVote doesExist(@Param("post") String post, @Param("voter") String voter);
	
	public PostVote findByPostAndVoter(Post post, RegisteredUser voter);
}
