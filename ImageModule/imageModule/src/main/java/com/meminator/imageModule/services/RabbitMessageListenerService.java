package com.meminator.imageModule.services;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.imageModule.configurations.RabbitConfig;
import com.meminator.imageModule.dao.ImageDAO;
import com.meminator.imageModule.dao.PostDAO;
import com.meminator.imageModule.dao.RegisteredUserDAO;
import com.meminator.imageModule.models.Image;
import com.meminator.imageModule.models.Post;
import com.meminator.imageModule.models.PostVM;
import com.meminator.imageModule.models.RegisteredUser;

@Service
public class RabbitMessageListenerService {
	 	
	@Autowired
    private RegisteredUserDAO registeredUserDAO;
    @Autowired
    private PostDAO postDAO;
    @Autowired
    private ImageDAO imageDAO;
    
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_USERS)
    public void receiveUser(final String username){
    	System.out.println("Primljen je user : " + username);
        registeredUserDAO.saveUser(new RegisteredUser(username));
    }
    
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_USERS_DELETE)
    public void deleteUser(final String username){
        registeredUserDAO.deleteUser(username);
    }
    
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_POST_DELETE)
    public void deletePost(final Long id){
        postDAO.deletePost(id);
    }


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_POST)
    public void receivePost(final PostVM postVM){
    	System.out.println(postVM.getPoster());
    	Optional<RegisteredUser> reg = registeredUserDAO.getUser(postVM.getPoster());
    	Optional<Image> img = imageDAO.getImage(postVM.getImageID());
        if(reg.isPresent() && img.isPresent()) {
            RegisteredUser user = reg.get();
            Image image = img.get();
            Post post = new Post();
            post.setId(postVM.getId());
            post.setMeme(image);
            post.setUser(user);
            postDAO.savePost(post);
        }
    }
}


