package com.meminator.postmodule.Models;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@Transactional
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID")
    private RegisteredUser user;
    private String imageURL;
    private Long imageID;
    @Column(name="timeStamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    private String info;
    private Integer upVote = 0;
    private Integer downVote = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private Post repost;

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @ManyToMany
    private List<Tag> tags = new ArrayList<Tag>();

    public Post() {
    }

    public Post(Long imageID, String info, Integer upVote, Integer downVote) {
        this.imageID = imageID;
        this.info = info;
        this.upVote = upVote;
        this.downVote = downVote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getUpVote() {
        return upVote;
    }

    public void setUpVote(Integer upVote) {
        this.upVote = upVote;
    }

    public Integer getDownVote() {
        return downVote;
    }

    public void setDownVote(Integer downVote) {
        this.downVote = downVote;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    @Nullable
    public Post getRepost() {
        return repost;
    }

    public void setRepost(@Nullable Post repost) {
        this.repost = repost;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        return id != null && id.equals(((Post) o).id);
    }
}
