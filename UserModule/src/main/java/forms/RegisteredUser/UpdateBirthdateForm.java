package forms.RegisteredUser;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UpdateBirthdateForm {
	
	@JsonFormat(pattern = "dd.MM.yyyy")
	private LocalDate date;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
