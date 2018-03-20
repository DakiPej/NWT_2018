package com.meminator.userModule.events;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.meminator.userModule.models.UserType;
import com.meminator.userModule.repositories.FollowRepository;
import com.meminator.userModule.repositories.RegisteredUserRepository;
import com.meminator.userModule.repositories.UserTokenRepository;
import com.meminator.userModule.repositories.UserTypeRepository;

@Component
public class DatabaseSeeder {
	
	private Logger logger = Logger.getLogger(DatabaseSeeder.class);
	private FollowRepository followRepository;
	private RegisteredUserRepository registeredUserRepository;
	private UserTokenRepository userTokenRepository;
	private UserTypeRepository userTypeRepository;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public DatabaseSeeder(
			FollowRepository followRepository, 
			RegisteredUserRepository registeredUserRepository,
			UserTokenRepository userTokenRepository, 
			UserTypeRepository userTypeRepository, JdbcTemplate jdbcTemplate) {
		System.out.println("KONSTRUKTOR");
		this.followRepository = followRepository;
		this.registeredUserRepository = registeredUserRepository;
		this.userTokenRepository = userTokenRepository;
		this.userTypeRepository = userTypeRepository;
		this.jdbcTemplate = jdbcTemplate;
	}

	@EventListener
	public void seed(final ContextRefreshedEvent event) {
		System.out.println("EVENT");
		userTypeTableSeed();
		followTableSeed();
	}
	
	private void userTypeTableSeed() {
		try {
			UserType ut = new UserType();
			ut.setTypeName("Regular user");
			ut = userTypeRepository.save(ut);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void followTableSeed() {
		try {
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
