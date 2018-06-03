package com.meminator.postmodule.Models;

import java.util.List;

public class PostVM {

    private Long id;
    private Long imageID;
    private String poster;
    private Long repostID;
    private List<String> tags;
    private String imageURL;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public PostVM() {
    }

    public PostVM(Long id, Long imageID, String poster) {
        this.id = id;
        this.imageID = imageID;
        this.poster = poster;
    }

    public PostVM(Post post){
        this.id = post.getId();
        this.poster = post.getUser().getUsername();
        this.imageID = post.getImageID();
    }

    public Long getRepostID() {
        return repostID;
    }

    public void setRepostID(Long repostID) {
        this.repostID = repostID;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
}
