package forms.FollowForms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FriendForm {
	@NotNull
	@Size(min = 3, max = 15)
	private String username;
	
	@NotNull
	@Size(min = 3, max = 15)
	private String friendUsername;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFriendUsername() {
		return friendUsername;
	}

	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
	}
}
