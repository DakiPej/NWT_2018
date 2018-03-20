package com.meminator.postmodule.Seeders;

import com.meminator.postmodule.Models.Post;
import com.meminator.postmodule.Models.RegisteredUser;
import com.meminator.postmodule.Models.Tag;
import com.meminator.postmodule.Repositories.IPostRepositroy;
import com.meminator.postmodule.Repositories.IRegisteredUserRepository;
import com.meminator.postmodule.Repositories.ITagRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder {

    private Logger logger = Logger.getLogger(DatabaseSeeder.class);
    private IPostRepositroy postRepositroy;
    private IRegisteredUserRepository registeredUserRepository;
    private ITagRepositroy tagRepositroy;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseSeeder(IPostRepositroy postRepositroy, IRegisteredUserRepository registeredUserRepository, ITagRepositroy tagRepositroy, JdbcTemplate jdbcTemplate) {
        this.postRepositroy = postRepositroy;
        this.registeredUserRepository = registeredUserRepository;
        this.tagRepositroy = tagRepositroy;
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        seedRegisteredUser();
        seedTag();
        seedPost();
    }

    private void seedTag() {
        String tu0 = "Funny", tu1= "Sad", tu2 = "Meme", tu3 = "2018";
        String sql = "SELECT * FROM tag t WHERE t.name IN (\"" + tu0 + "\", \"" + tu1 + "\", \"" + tu2 + "\", \"" + tu3 + "\")";
        List<Tag> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            Tag t0 = new Tag(tu0);
            Tag t1 = new Tag(tu1);
            Tag t2 = new Tag(tu2);
            Tag t3 = new Tag(tu3);
            tagRepositroy.saveAll(Arrays.asList(t0,t1,t2,t3));
            logger.info("Tag table seeded");
        } else {
            logger.trace("Tag Seeding Not Required.");
        }
    }

    private void seedRegisteredUser() {

        String du0 = "dakipej", du1 = "sbecirovic", du2 = "tdzirlo", du3 = "pipi", du4 = "seka", du5 = "aco";
        String sql = "SELECT * FROM registered_user u WHERE u.username IN (\"" + du0 + "\", \"" + du1 + "\", \"" + du2 + "\", \"" + du3 + "\", \"" + du4 + "\", \"" + du5 + "\")";
        List<RegisteredUser> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            RegisteredUser u0 = new RegisteredUser(du0);
            RegisteredUser u1 = new RegisteredUser(du1);
            RegisteredUser u2 = new RegisteredUser(du2);
            RegisteredUser u3 = new RegisteredUser(du3);
            RegisteredUser u4 = new RegisteredUser(du4);
            RegisteredUser u5 = new RegisteredUser(du5);
            registeredUserRepository.saveAll(Arrays.asList(u0,u1,u2,u3,u4,u5));
            logger.info("RegisteredUser table seeded");
        }else {
            logger.trace("RegistratedUser Seeding Not Required.");
        }

    }

    private void seedPost() {
        String sql = "SELECT * FROM post p";
        List<Post> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<RegisteredUser> users = registeredUserRepository.findAll();
            List<Post> posts = new ArrayList<>();
            Integer id = 1;
            List<Tag> tags = tagRepositroy.findAll();
            for(RegisteredUser i : users){
                Post p = new Post();
                p.setImageID(Integer.toUnsignedLong((id%2)+1));
                id++;
                p.setImageURL("https://tinyurl.com/ycxmgj4e");
                p.setInfo("Seed");
                p.setUser(i);
                posts.add(p);
                for(Tag t: tags) {
                    p.addTag(t);
                }
            }
            tagRepositroy.saveAll(tags);
            postRepositroy.saveAll(posts);
            registeredUserRepository.saveAll(users);
            logger.info("Post table seeded");
        }else {
            logger.trace("Post Seeding Not Required.");
        }
    }


}
