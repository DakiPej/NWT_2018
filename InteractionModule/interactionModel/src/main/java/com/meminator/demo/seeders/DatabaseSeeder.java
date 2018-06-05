package com.meminator.demo.seeders;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
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
import com.meminator.demo.services.CommentService;
import com.meminator.demo.services.CommentVoteService;
import com.meminator.demo.services.PostVoteService;

@Component
public class DatabaseSeeder {
	
	Logger logger = Logger.getLogger(DatabaseSeeder.class);
	
	CommentRepository commentRepo; 
	CommentVoteRepository commentVoteRepo;
	NotificationRepository notificationRepo;
	NotificationTypeRepository notificationTypeRepo;
	PostRepository postRepo;
	PostVoteRepository postVoteRepo;
	RegisteredUserRepository registeredUserRepo;
	
	CommentService commentService ; 
	CommentVoteService commentVoteService ; 
	PostVoteService postVoteService ; 
	
	@Autowired
	public DatabaseSeeder(
			CommentRepository commentRepo, 
			CommentVoteRepository commentVoteRepo,
			NotificationRepository notificationRepo,
			NotificationTypeRepository notificationTypeRepo,
			PostRepository postRepo,
			PostVoteRepository postVoteRepo,
			RegisteredUserRepository registeredUserRepo, 
			
			CommentService commentService, 
			CommentVoteService commentVoteService, 
			PostVoteService postVoteService)	{
		
		this.commentRepo = commentRepo; 
		this.commentVoteRepo = commentVoteRepo;
		this.notificationRepo = notificationRepo;
		this.notificationTypeRepo = notificationTypeRepo;
		this.postRepo = postRepo;
		this.postVoteRepo = postVoteRepo;
		this.registeredUserRepo = registeredUserRepo;
		
		this.commentService = commentService ; 
		this.commentVoteService = commentVoteService ; 
		this.postVoteService = postVoteService ; 
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
        	Calendar cal = Calendar.getInstance() ; 
        	cal.setTime(new Date());
        	cal.add(Calendar.DATE, -1);
        	
        	user.setId((long)1);
        	user.setUsername("dakipej");
        	user.setLastTimeChecked(cal.getTime());
        	registeredUserRepo.save(user);
        	
        	user = new RegisteredUser();
        	user.setId((long)2);
        	user.setUsername("sbecirovic");
        	user.setLastTimeChecked(cal.getTime());
        	registeredUserRepo.save(user);
        	
        	user = new RegisteredUser();
        	user.setId((long)3);
        	user.setUsername("tdzirlo");
        	user.setLastTimeChecked(cal.getTime());
        	registeredUserRepo.save(user);
        	
        	user = new RegisteredUser();
        	user.setId((long)4);
        	user.setUsername("pipi");
        	user.setLastTimeChecked(cal.getTime());
        	registeredUserRepo.save(user);
        	
        	user = new RegisteredUser();
        	user.setId((long)5);
        	user.setUsername("seka");
        	user.setLastTimeChecked(cal.getTime());
        	registeredUserRepo.save(user);
        	
        	user = new RegisteredUser();
        	user.setId((long)6);
        	user.setUsername("aco");
        	user.setLastTimeChecked(cal.getTime());
        	registeredUserRepo.save(user);
		}
	}
	
	void seedNotificationTypeTable()	{
		if(((List<NotificationType>) this.notificationTypeRepo.findAll()).isEmpty())	{
			NotificationType notificationType = new NotificationType(); 
			notificationType.setTypeName("Followed");
			this.notificationTypeRepo.save(notificationType); 
			
			notificationType = new NotificationType();
			notificationType.setTypeName("Commented");
			this.notificationTypeRepo.save(notificationType); 
			
			notificationType = new NotificationType();
			notificationType.setTypeName("Post vote");
			this.notificationTypeRepo.save(notificationType); 
			
			notificationType = new NotificationType();
			notificationType.setTypeName("Comment vote");
			this.notificationTypeRepo.save(notificationType);
			
			notificationType = new NotificationType();
			notificationType.setTypeName("Post repost");
			this.notificationTypeRepo.save(notificationType); 
		}
	}
	
	void seedNotificationTable()	{
		if(((List<Notification>) this.notificationRepo.findAll()).isEmpty())	{
			//Notification notification = new Notification(new RegisteredUser(1), new NotificationType(1));
			//this.notificationRepo.save(notification); 
		}
	}
	void seedPostTable()	{
		if(((List<Post>) this.postRepo.findAll()).isEmpty())	{
			for(int i = 1 ; i <= 6 ; i ++ )	{
				Post post = new Post(new RegisteredUser(i));
				post.setId((long)i);
				this.postRepo.save(post); 
			}
		}
	}
	void seedCommentTable()	{
		if(((List<Comment>) this.commentRepo.findAll()).isEmpty())	{
			this.commentService.createComment((long)1, "aco", "Jedan komentar od ACE na DAKIJEV post.") ; 
			this.commentService.createComment((long)2, "seka", "Drugi komentar od SEKE na SEILIN post.") ;
			this.commentService.createComment((long)3, "pipi", "Treci komentar od PIPIA na TIMUROV post.") ;
			this.commentService.createComment((long)4, "tdzirlo", "Cetvrti komentar od TIMURA na PIPIJEV post") ; 
			this.commentService.createComment((long)5, "sbecirovic", "Peti komentar od SEILE na SEKIN post") ; 
			this.commentService.createComment((long)6, "dakipej", "Sesti komentar od DAKIJA na ACIN post") ;
			//Comment comm = new Comment(new Post(1), new RegisteredUser(1));
			//this.commentRepo.save(comm); 
		}
	}
	void seedPostVoteTable()	{
		if(((List<PostVote>) this.postVoteRepo.findAll()).isEmpty())	{
			//PostVote postVote = new PostVote(new RegisteredUser(1), new Post(1));
			//this.postVoteRepo.save(postVote); 
			this.postVoteService.createPostVote((long)1, "aco", true) ;
			this.postVoteService.createPostVote((long)2, "seka", true) ;
			this.postVoteService.createPostVote((long)3, "pipi", true) ;
			this.postVoteService.createPostVote((long)4, "tdzirlo", true) ;
			this.postVoteService.createPostVote((long)5, "sbecirovic", true) ;
			this.postVoteService.createPostVote((long)6, "dakipej", true) ;
		}
	}
	
	void seedCommentVoteTable()	{
		if(((List<CommentVote>) this.commentVoteRepo.findAll()).isEmpty())	{
			//CommentVote commentVote = new CommentVote(new Comment(1), new RegisteredUser(1));
			//this.commentVoteRepo.save(commentVote); 
			this.commentVoteService.createCommentVote((long)1, "dakipej", true) ;
			this.commentVoteService.createCommentVote((long)2, "sbecirovic", true) ;
			this.commentVoteService.createCommentVote((long)3, "tdzirlo", true) ;
			this.commentVoteService.createCommentVote((long)4, "pipi", true) ;
			this.commentVoteService.createCommentVote((long)5, "seka", true) ;
			this.commentVoteService.createCommentVote((long)6, "aco", true) ;
		}
	}
}
