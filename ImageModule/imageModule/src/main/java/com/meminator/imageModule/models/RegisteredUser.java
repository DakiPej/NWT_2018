package com.meminator.imageModule.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="registered_user")
public class RegisteredUser {

	@Id
	@Column(unique=true)
	private Long id;
	
	@OneToOne(targetEntity=Image.class)
    @JoinColumn(name="profile_pictureid")
    private Image profile;
	
	@NotNull
	@Column(unique=true)
	@Size(max = 15)
	private String username;

	public RegisteredUser() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Image getProfile() {
		return profile;
	}

	public void setProfile(Image profile) {
		this.profile = profile;
	}

	public RegisteredUser(Long id, Image profile, String username) {
		this.id = id;
		this.profile = profile;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
