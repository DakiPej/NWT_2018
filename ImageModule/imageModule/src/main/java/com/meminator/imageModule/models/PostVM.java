package com.meminator.imageModule.models;

public class PostVM {

    private Long id;
    private Long imageID;
    private String poster;
    private PostVM repost;

    public PostVM() {
    }

    public PostVM(Long id, Long imageID, String poster, PostVM repost) {
        this.id = id;
        this.imageID = imageID;
        this.poster = poster;
        this.repost = repost;
        
    }

    public PostVM(Post post){
        this.id = post.getId();
        this.poster = post.getUser().getUsername();
        this.imageID = post.getId();
    }

    public PostVM getRepost() {
        return repost;
    }

    public void setRepost(PostVM repost) {
        this.repost = repost;
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