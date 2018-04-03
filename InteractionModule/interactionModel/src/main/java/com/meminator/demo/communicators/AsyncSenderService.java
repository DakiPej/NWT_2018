package com.meminator.demo.communicators;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncSenderService {
	
	RabbitTemplate rabbitTemplate; 
	
	@Autowired
	public AsyncSenderService(final RabbitTemplate rabbitTemplate)	{
		this.rabbitTemplate = rabbitTemplate; 
	}
	
	public void sendPostVote(long id, int upVoteCount, int downVoteCount)	{
		rabbitTemplate.convertAndSend(
				"vote-que-exchange", 
				"post.vote", 
				new PostVoteVM(id, upVoteCount, downVoteCount)
				);
	}
	
	private static class PostVoteVM	{
		public long postId; 
		public int upVoteCount; 
		public int downVoteCount;
		
		public PostVoteVM(long postId, int upVoteCount, int downVoteCount)	{
			this.postId = postId; 
			this.upVoteCount = upVoteCount; 
			this.downVoteCount = downVoteCount;
		}
	}
}
