package com.meminator.postmodule.Services;

import com.meminator.postmodule.Models.PostVM;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public AsyncSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendPost(PostVM postVM){

        rabbitTemplate.convertAndSend("posts-queue-exchange","post.create", postVM);

    }

    public void deletePost(Long postID){
        rabbitTemplate.convertAndSend("posts-queue-exchange", "post.delete", postID);
    }

}
