package com.meminator.demo.communicators;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.meminator.demo.configurations.AsyncConfiguration;

@Service
public class AsyncReceiverService {
	
	@RabbitListener(queues=AsyncConfiguration.QUEUE_NAME_VOTE)
	public void receivePostVoteUpdate(final PostVoteVM postVoteVM)	{
		System.out.println("Post koji treba da se update-uje jeste : " + Long.toString(postVoteVM.postId));
	}
	
	private static class PostVoteVM	{
		public long postId; 
		public int upVoteCount; 
		public int downVoteCount;
	}
}
