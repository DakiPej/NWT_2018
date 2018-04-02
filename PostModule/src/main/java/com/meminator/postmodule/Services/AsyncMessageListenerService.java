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
    public void receiveVote(final VoteVerification voteVerification){
        if(!voteVerification.isPassed()){
            Optional<Post> post = postDAO.getPost(voteVerification.getPostID());
            if(post.isPresent()) {
                Post tmp = post.get();
                if (voteVerification.isUp()) {
                    tmp.setUpVote(tmp.getUpVote() - 1);
                } else {
                    tmp.setDownVote(tmp.getDownVote() - 1);
                }
                postDAO.savePost(tmp);
            }
        }
    }

}
