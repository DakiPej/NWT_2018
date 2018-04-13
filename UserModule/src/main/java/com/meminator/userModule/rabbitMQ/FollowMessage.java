package com.meminator.userModule.rabbitMQ;

public class FollowMessage {
	private String user;
	private String followedUser;
	
	public FollowMessage(String user, String followedUser) {
		this.user = user;
		this.followedUser = followedUser;
	}
	public FollowMessage() {
		
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(String followedUser) {
		this.followedUser = followedUser;
	}
}
