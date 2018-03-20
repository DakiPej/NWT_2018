package com.meminator.userModule.events;

import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.meminator.userModule.models.Follow;
import com.meminator.userModule.models.RegisteredUser;
import com.meminator.userModule.models.UserToken;
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
	public void seed(ContextRefreshedEvent event) {
		System.out.println("EVENT");
		
		if(userTypeRepository.count() == 0) 
			userTypeTableSeed();
		if(registeredUserRepository.count() == 0)
			registeredUserSeed();
		if(userTokenRepository.count() == 0)
			userTokenSeed();
		if(followRepository.count() == 0)
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
	
	private void registeredUserSeed() {
		try {
			RegisteredUser ru = new RegisteredUser();
			
			ru.setUserTypeID(userTypeRepository.findUserTypeById(Long.valueOf(1)));
			ru.setUsername("dakipej");
			ru.setEmail("dakipej@gmail.com");
			ru.setPassword("dakipej");
			ru = registeredUserRepository.save(ru);
			
			ru = new RegisteredUser();
			ru.setUserTypeID(userTypeRepository.findUserTypeById(Long.valueOf(1)));
			ru.setUsername("sbecirovic");
			ru.setEmail("sbecirovic@gmail.com");
			ru.setPassword("sbecirovic");
			ru = registeredUserRepository.save(ru);
			
			ru = new RegisteredUser();
			ru.setUserTypeID(userTypeRepository.findUserTypeById(Long.valueOf(1)));
			ru.setUsername("tdzirlo");
			ru.setEmail("tdzirlo@gmail.com");
			ru.setPassword("tdzirlo");
			ru = registeredUserRepository.save(ru);
			
			ru = new RegisteredUser();
			ru.setUserTypeID(userTypeRepository.findUserTypeById(Long.valueOf(1)));
			ru.setUsername("pipi");
			ru.setEmail("pipi@gmail.com");
			ru.setPassword("pipi");
			ru = registeredUserRepository.save(ru);
			
			ru = new RegisteredUser();
			ru.setUserTypeID(userTypeRepository.findUserTypeById(Long.valueOf(1)));
			ru.setUsername("seka");
			ru.setEmail("seka@gmail.com");
			ru.setPassword("seka");
			ru = registeredUserRepository.save(ru);
			
			ru = new RegisteredUser();
			ru.setUserTypeID(userTypeRepository.findUserTypeById(Long.valueOf(1)));
			ru.setUsername("aco");
			ru.setEmail("aco@gmail.com");
			ru.setPassword("aco");
			ru = registeredUserRepository.save(ru);
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void userTokenSeed() {
		
		try {
			Calendar calendarDate = Calendar.getInstance(
			TimeZone.getTimeZone("UTC"));
 			calendarDate.set(Calendar.YEAR, 2017);
			calendarDate.set(Calendar.MONTH, 10);
			calendarDate.set(Calendar.DAY_OF_MONTH, 15);
			
			UserToken ut = new UserToken();
			String str = RandomStringUtils.randomAlphanumeric(32);
			ut.setToken(String.valueOf(str));
			ut.setExpirationDateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			ut.setUserID(registeredUserRepository.getUserByUsername("dakipej"));
			ut = userTokenRepository.save(ut);
			
			ut = new UserToken();
			str = RandomStringUtils.randomAlphanumeric(32);
			ut.setToken(String.valueOf(str));
			ut.setExpirationDateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			ut.setUserID(registeredUserRepository.getUserByUsername("sbecirovic"));
			ut = userTokenRepository.save(ut);
			
			ut = new UserToken();
			str = RandomStringUtils.randomAlphanumeric(32);
			ut.setToken(String.valueOf(str));
			ut.setExpirationDateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			ut.setUserID(registeredUserRepository.getUserByUsername("tdzirlo"));
			ut = userTokenRepository.save(ut);
			
			ut = new UserToken();
			str = RandomStringUtils.randomAlphanumeric(32);
			ut.setToken(String.valueOf(str));
			ut.setExpirationDateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			ut.setUserID(registeredUserRepository.getUserByUsername("pipi"));
			ut = userTokenRepository.save(ut);
			
			ut = new UserToken();
			str = RandomStringUtils.randomAlphanumeric(32);
			ut.setToken(String.valueOf(str));
			ut.setExpirationDateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			ut.setUserID(registeredUserRepository.getUserByUsername("seka"));
			ut = userTokenRepository.save(ut);
			
			ut = new UserToken();
			str = RandomStringUtils.randomAlphanumeric(32);
			ut.setToken(String.valueOf(str));
			ut.setExpirationDateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			ut.setUserID(registeredUserRepository.getUserByUsername("aco"));
			ut = userTokenRepository.save(ut);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void followTableSeed() {
		try {
			Follow f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("dakipej"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("aco"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("sbecirovic"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("aco"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("pipi"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("tdzirlo"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("tdzirlo"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("pipi"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("sbecirovic"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("dakipej"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("dakipej"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("sbecirovic"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("dakipej"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("aco"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("aco"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("dakipej"));
			f = followRepository.save(f);
			
			f = new Follow();
			f.setUserID(registeredUserRepository.getUserByUsername("seka"));
			f.setFollowingUserID(registeredUserRepository.getUserByUsername("pipi"));
			f = followRepository.save(f);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
