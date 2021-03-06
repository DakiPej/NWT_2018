package com.meminator.userModule.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class UserToken {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@NotNull
	private RegisteredUser userID;
	
	@NotNull
	@Size(min = 32, max = 32)
	private String token;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public RegisteredUser getUserID() {
		return userID;
	}

	public void setUserID(RegisteredUser userID) {
		this.userID = userID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationDateTime() {
		return expirationDateTime;
	}

	public void setExpirationDateTime(Date expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}
	public UserToken() {
		
	}
}
