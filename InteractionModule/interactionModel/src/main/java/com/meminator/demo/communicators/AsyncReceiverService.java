package com.meminator.demo.communicators;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.meminator.demo.configurations.AsyncConfiguration;
import com.meminator.demo.services.PostService;
import com.meminator.demo.services.PostVoteService;
import com.meminator.demo.services.RegisteredUserService;

@Service
public class AsyncReceiverService {
	
	RegisteredUserService registeredUserService;
	PostVoteService postVoteService; 
	PostService postService; 
	
	@Autowired
	public void setRegisteredUserService(RegisteredUserService registeredUserService)	{
		this.registeredUserService = registeredUserService; 
	}
	@Autowired
	public void setPostVoteService(PostVoteService postVoteService)	{
		this.postVoteService = postVoteService; 
	}
	@Autowired
	public void setPostService(PostService postService)	{
		this.postService = postService;
	}
	
	@RabbitListener(queues = AsyncConfiguration.QUEUE_USERS_TO_BE_DELETED)
	public void receiveUserToBeDeleted(final String username)	{
		this.registeredUserService.deleteUser(username); 
	}
	
	@RabbitListener(queues = AsyncConfiguration.QUEUE_USERS_TO_BE_ADDED)
	public void receiveAddedUsers	(final String username)	{
		this.registeredUserService.createNewRegisteredUser(username); 
	}
	
	@RabbitListener(queues = AsyncConfiguration.QUEUE_POSTS_TO_BE_ADDED)
	public void receiveNewPost(final PostInformation post)	{
		this.postService.createPost(post.postId, post.username);
	}
	@RabbitListener(queues = AsyncConfiguration.QUEUE_USERS_TO_BE_DELETED)
	public void receivePostToBeDeleted(final PostInformation postInformation)	{
		this.postService.deletePost(postInformation.postId); 
	}
	/*@RabbitListener(queues = AsyncConfiguration.QUEUE_POST_VOTE)
	public void receivePostVoteUpdate(final PostVoteVM postVoteVM)	{
		System.out.println("Post koji treba da se update-uje jeste : " + Long.toString(postVoteVM.postId));
	}*/
	
	private static class RequiredUsername	{
		public String username; 
	}
	private static class PostInformation	{
		public long postId; 
		public String username; 
	}
	private static class PostVoteVM	{
		public long postId; 
		public int upVoteCount; 
		public int downVoteCount;
	}
}
