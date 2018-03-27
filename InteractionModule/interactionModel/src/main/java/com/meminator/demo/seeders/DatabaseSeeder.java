package com.meminator.demo.seeders;

import org.apache.log4j.Logger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.meminator.demo.models.Comment;
import com.meminator.demo.models.CommentVote;
import com.meminator.demo.models.Notification;
import com.meminator.demo.models.NotificationType;
import com.meminator.demo.models.Post;
import com.meminator.demo.models.PostVote;
import com.meminator.demo.models.RegisteredUser;
import com.meminator.demo.repositories.CommentRepository;
import com.meminator.demo.repositories.CommentVoteRepository;
import com.meminator.demo.repositories.NotificationRepository;
import com.meminator.demo.repositories.NotificationTypeRepository;
import com.meminator.demo.repositories.PostRepository;
import com.meminator.demo.repositories.PostVoteRepository;
import com.meminator.demo.repositories.RegisteredUserRepository;

@Component
public class DatabaseSeeder {
	
	//Samo jedan mali testni komentar bruda moi 
	Logger logger = Logger.getLogger(DatabaseSeeder.class);
	
	CommentRepository commentRepo; 
	CommentVoteRepository commentVoteRepo;
	NotificationRepository notificationRepo;
	NotificationTypeRepository notificationTypeRepo;
	PostRepository postRepo;
	PostVoteRepository postVoteRepo;
	RegisteredUserRepository registeredUserRepo;
	
	@Autowired
	public DatabaseSeeder(
			CommentRepository commentRepo, 
			CommentVoteRepository commentVoteRepo,
			NotificationRepository notificationRepo,
			NotificationTypeRepository notificationTypeRepo,
			PostRepository postRepo,
			PostVoteRepository postVoteRepo,
			RegisteredUserRepository registeredUserRepo)	{
		
		this.commentRepo = commentRepo; 
		this.commentVoteRepo = commentVoteRepo;
		this.notificationRepo = notificationRepo;
		this.notificationTypeRepo = notificationTypeRepo;
		this.postRepo = postRepo;
		this.postVoteRepo = postVoteRepo;
		this.registeredUserRepo = registeredUserRepo;
		
	}
	
	@EventListener
	public void seed(ContextRefreshedEvent event) 	{
		seedRegisteredUserTable(); 
		seedNotificationTypeTable(); 
		seedNotificationTable(); 
		seedPostTable(); 
		seedCommentTable(); 
		seedPostVoteTable(); 
		seedCommentVoteTable(); 
		
	}
	
	void seedRegisteredUserTable()	{
		if(((List<RegisteredUser>) this.registeredUserRepo.findAll()).isEmpty()){
        	RegisteredUser user = new RegisteredUser();
        	user.setId((long)1);
        	user.setUsername("dakipej");
        	registeredUserRepo.save(user);
        	user = new RegisteredUser();
        	user.setId((long)2);
        	user.setUsername("sbecirovic");
        	registeredUserRepo.save(user);
        	user = new RegisteredUser();
        	user.setId((long)3);
        	user.setUsername("tdzirlo");
        	registeredUserRepo.save(user);
        	user = new RegisteredUser();
        	user.setId((long)4);
        	user.setUsername("pipi");
        	registeredUserRepo.save(user);
        	user = new RegisteredUser();
        	user.setId((long)5);
        	user.setUsername("seka");
        	registeredUserRepo.save(user);
        	user = new RegisteredUser();
        	user.setId((long)6);
        	user.setUsername("aco");
        	registeredUserRepo.save(user);
		}
	}
	
	void seedNotificationTypeTable()	{
		if(((List<NotificationType>) this.notificationTypeRepo.findAll()).isEmpty())	{
			NotificationType notificationType = new NotificationType(); 
			notificationType.setTypeName("Followed");
			this.notificationTypeRepo.save(notificationType); 
			notificationType.setTypeName("Commented");
			this.notificationTypeRepo.save(notificationType); 
			notificationType.setTypeName("Post liked");
			this.notificationTypeRepo.save(notificationType); 
			notificationType.setTypeName("Comment liked");
			this.notificationTypeRepo.save(notificationType);
			notificationType.setTypeName("Post repost");
			this.notificationTypeRepo.save(notificationType); 
		}
	}
	
	void seedNotificationTable()	{
		if(((List<Notification>) this.notificationRepo.findAll()).isEmpty())	{
			Notification notification = new Notification(new RegisteredUser(1), new NotificationType(1));
			this.notificationRepo.save(notification); 
		}
	}
	void seedPostTable()	{
		if(((List<Post>) this.postRepo.findAll()).isEmpty())	{
			Post post = new Post(new RegisteredUser(1));
			post.setId((long)1);
			this.postRepo.save(post); 
		}
	}
	void seedCommentTable()	{
		if(((List<Comment>) this.commentRepo.findAll()).isEmpty())	{
			Comment comm = new Comment(new Post(1), new RegisteredUser(1));
			this.commentRepo.save(comm); 
			System.out.println("Novi komentar je dodan. ");
		}
	}
	void seedPostVoteTable()	{
		if(((List<PostVote>) this.postVoteRepo.findAll()).isEmpty())	{
			PostVote postVote = new PostVote(new RegisteredUser(1), new Post(1));
			this.postVoteRepo.save(postVote); 
		}
	}
	
	void seedCommentVoteTable()	{
		if(((List<CommentVote>) this.commentVoteRepo.findAll()).isEmpty())	{
			CommentVote commentVote = new CommentVote(new Comment(1), new RegisteredUser(1));
			this.commentVoteRepo.save(commentVote); 
		}
	}
}
