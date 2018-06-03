package forms.RegisteredUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResetPasswordForm {
	@NotNull
	@Size(min = 8, max = 20)
	private String oldPassword;
	
	@NotNull
	@Size(min = 8, max = 20)
	private String newPassword;
	
	@NotNull
	@Size(min = 8, max = 20)
	private String newPasswordR;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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
