package com.meminator.demo.models;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue
	long id; 
	
	@ManyToOne
	RegisteredUser userId; 
	
	@ManyToOne
	NotificationType notificationTypeId; 
	
	@Temporal(TemporalType.TIMESTAMP)
	Date creationMoment;
	
	String contet;
	
	Boolean checked; 
	
	public Notification()	{
		
	}
	public Notification(RegisteredUser regUser, NotificationType notificationType)	{
		userId = regUser; 
		notificationTypeId = notificationType; 
	}
	public Notification(long id)	{
		this.id = id; 
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getUserId() {
		return userId;
	}

	public void setUserId(RegisteredUser userId) {
		this.userId = userId;
	}

	public NotificationType getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(NotificationType notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public String getContet() {
		return contet;
	}

	public void setContet(String contet) {
		this.contet = contet;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
