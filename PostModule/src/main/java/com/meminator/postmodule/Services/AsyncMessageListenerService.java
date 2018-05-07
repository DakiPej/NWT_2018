package com.meminator.postmodule.Services;

import com.meminator.postmodule.Configurations.AsyncConfig;
import com.meminator.postmodule.Controllers.ServiceInstanceRestController;
import com.meminator.postmodule.DAO.PostDAO;
import com.meminator.postmodule.DAO.RegisteredUserDAO;
import com.meminator.postmodule.Models.Post;
import com.meminator.postmodule.Models.RegisteredUser;
import com.meminator.postmodule.Models.VoteVerification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AsyncMessageListenerService {

    @Autowired
    private RegisteredUserDAO registeredUserDAO;
    @Autowired
    private PostDAO postDAO;

    @RabbitListener(queues = AsyncConfig.QUEUE_NAME_USERS)
    public void receiveUser(final String username){
        registeredUserDAO.createUser(new RegisteredUser(username));
    }

    @RabbitListener(queues = AsyncConfig.QUEUE_NAME_VOTE)
    public void receiveVote(final PostVoteVM postVoteVM){
        Optional<Post> opt = postDAO.getPost(postVoteVM.postId);
        if(opt.isPresent()){
            Post post = opt.get();
            post.setDownVote(postVoteVM.downVoteCount);
            post.setUpVote(postVoteVM.upVoteCount);
            postDAO.savePost(post);
        }
    }

    private static class PostVoteVM	{
        public long postId;
        public int upVoteCount;
        public int downVoteCount;
    }

}
