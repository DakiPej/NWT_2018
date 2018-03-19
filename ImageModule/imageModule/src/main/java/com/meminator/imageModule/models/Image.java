package com.meminator.imageModule.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Image")
public class Image {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Lob
	@Column(name="data")
	private byte[] data;
	
	@ManyToOne(targetEntity=ImageType.class)
    @JoinColumn(name="typeID")
    private ImageType tip;

	public Image(Long id, byte[] data, ImageType tip) {
		this.id = id;
		this.data = data;
		this.tip = tip;
	}

	public Image() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public ImageType getTip() {
		return tip;
	}

	public void setTip(ImageType tip) {
		this.tip = tip;
	}
	
	
	

}
