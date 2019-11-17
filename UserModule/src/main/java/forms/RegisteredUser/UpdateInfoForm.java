package forms.RegisteredUser;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateInfoForm {
	@Size(max = 255)
	private String info;

	@Size(max = 15)
	private String firstName;
	
	@Size(max = 20)
	private String lastName;
	
	@Size(max = 30)
	@Email(message = "Email is not valid.")
	private String email;
	

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
}
