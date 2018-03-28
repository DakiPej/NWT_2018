package com.meminator.imageModule.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.Image;
import com.meminator.imageModule.models.RegisteredUser;
import com.meminator.imageModule.repositories.ImageRepository;

@Repository
public class ImageDAO extends BaseDAO<Image, ImageRepository >{
	
		public Image createImage(Image image){
	        return this.repo.save(image);
	    }

	    public Optional<Image> getImage(Long id){
	        return this.repo.findById(id);
	    }

	    public boolean deleteImage(Long id){
	        this.repo.deleteById(id);
	        return !this.repo.findById(id).isPresent();
	    }

}
