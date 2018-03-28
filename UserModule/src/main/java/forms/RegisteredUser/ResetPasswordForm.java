package forms.RegisteredUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResetPasswordForm {
	@NotNull
	@Size(min = 3, max = 15)
	private String username;
	
	@NotNull
	@Size(min = 8, max = 20)
	private String newPassword;
	
	@NotNull
	@Size(min = 8, max = 20)
	private String newPasswordR;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordR() {
		return newPasswordR;
	}

	public void setNewPasswordR(String newPasswordR) {
		this.newPasswordR = newPasswordR;
	}
}
