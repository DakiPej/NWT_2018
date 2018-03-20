package com.meminator.imageModule.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="image_type")
public class ImageType {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="type_name")
	@Size(max = 20)
	private String typeName;

	public ImageType(Long id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	public ImageType() {
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
