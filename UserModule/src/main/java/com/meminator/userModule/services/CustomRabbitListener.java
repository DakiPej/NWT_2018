package com.meminator.userModule.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.meminator.userModule.rabbitMQ.FollowMessage;

@Service
public class CustomRabbitListener {
	
	//@RabbitListener(queues = "followQueue")
    public void receiveMessage(final FollowMessage followMessage) {
		System.out.println(followMessage.toString());
	}
}
