package forms.RegisteredUser;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserForm {
	
	@Size(max = 15)
	private String firstName;
	
	@Size(max = 20)
	private String lastName;
	
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@Size(min = 3, max = 15)
	@NotNull
	private String username;
	
	@NotNull
	@Size(max = 30)
	@Email(message = "Email is not valid.")
	private String email;
	
	@NotNull
	@Size(min = 8, max = 20)
	private String password;

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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
