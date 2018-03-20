package com.meminator.postmodule.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post){
        posts.remove(post);
        post.setUser(null);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public RegisteredUser() {
    }

    public RegisteredUser(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
