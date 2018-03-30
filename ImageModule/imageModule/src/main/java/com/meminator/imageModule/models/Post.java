package com.meminator.imageModule.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="post")
public class Post {
	
	@Id
	@Column(unique=true)
	private Long id;
	
	@ManyToOne(targetEntity=RegisteredUser.class)
    @JoinColumn(name="user_id")
    private RegisteredUser user;
	
	@ManyToOne(targetEntity=Image.class)
    @JoinColumn(name="meme_id")
    private Image meme ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public Image getMeme() {
		return meme;
	}

	public void setMeme(Image meme) {
		this.meme = meme;
	}

	public Post(Long id, RegisteredUser user, Image meme) {
		this.id = id;
		this.user = user;
		this.meme = meme;
	}

	public Post() {
	}
	
	

}
