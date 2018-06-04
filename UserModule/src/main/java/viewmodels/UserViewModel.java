package viewmodels;

public class UserViewModel {
	
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String info;
	private Integer followingCount;
	private Integer followedByCount;


	public UserViewModel(){

	}

	public UserViewModel(String firstName, String lastName, String username, String email, String info, Integer followingCount, Integer followedByCount){
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.info = info;
		this.followedByCount = followedByCount;
		this.followingCount = followingCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}

	public void setFollowedByCount(Integer followedByCount) {
		this.followedByCount = followedByCount;
	}
	
	public Integer getFollowedByCount() {
		return followedByCount;
	}

	public Integer getFollowingCount() {
		return followingCount;
	}
	
	public void setFollowingCount(Integer followingCount) {
		this.followingCount = followingCount;
	}
}
