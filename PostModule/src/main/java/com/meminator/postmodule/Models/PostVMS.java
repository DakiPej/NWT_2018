package com.meminator.postmodule.Models;

import java.util.List;

import org.aspectj.weaver.patterns.PerThisOrTargetPointcutVisitor;

public class PostVMS {

    private Long id;
    private Long imageID;
    private String poster;
    private PostVMS repost = null;

    public PostVMS() {
    }

    public PostVMS(Long id, Long imageID, String poster) {
        this.id = id;
        this.imageID = imageID;
        this.poster = poster;
    }

    public PostVMS(Post post){
        this.id = post.getId();
        this.poster = post.getUser().getUsername();
        this.imageID = post.getImageID();
        this.repost = (post.getRepost()==null?null:new PostVMS(post.getRepost()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setRepost(PostVMS repost) {
        this.repost = repost;
    }
    
    public PostVMS getRepost() {
        return repost;
    }
}
