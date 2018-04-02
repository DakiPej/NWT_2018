package forms.RegisteredUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateInfoForm {
	@NotNull
	private String username;
	
	@Size(max = 255)
	private String info;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
