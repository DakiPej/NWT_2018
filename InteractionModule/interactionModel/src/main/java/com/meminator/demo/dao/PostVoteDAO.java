package com.meminator.demo.dao;

import org.springframework.stereotype.Repository;

import com.meminator.demo.models.Post;
import com.meminator.demo.models.PostVote;
import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.repositories.PostVoteRepository;

@Repository
public class PostVoteDAO extends BaseDAO<PostVote, PostVoteRepository>{
	
	public boolean createPostVote(PostVote postVote)	{
		try {
			this.repo.save(postVote);
		} catch (Exception e) {
			return false;
		}
		return true; 
	}
	public PostVote findPostVoteByPostAndVoter(String post, String voter)	{
		return this.repo.doesExist(post, voter);
	}
	public PostVote findByPostAndVoter(Post post, RegisteredUser voter)	{
		return this.repo.findByPostAndVoter(post, voter);
	}
}
