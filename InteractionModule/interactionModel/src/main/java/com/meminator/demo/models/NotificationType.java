package com.meminator.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class NotificationType {

	@Id
	@GeneratedValue
	long id; 
	
	@NotNull
	String typeName; 
	
	public NotificationType()	{
		
	}
	public NotificationType(long id)	{
		this.id = id; 
	}
	
	public NotificationType(NotificationType notificationType)	{
		this.id = notificationType.getId(); 
		this.typeName = notificationType.getTypeName();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
