package com.meminator.postmodule.Models;

public class VoteVerification {

    private Long postID;
    private boolean passed;
    private boolean up;

    public VoteVerification(Long postID, boolean passed, boolean up) {
        this.passed = passed;
        this.up = up;
        this.postID = postID;
    }

    public VoteVerification() {

    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }
}
