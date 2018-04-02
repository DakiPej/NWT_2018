package com.meminator.imageModule.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.ImageType;
import com.meminator.imageModule.repositories.ImageTypeRepository;

@Repository 
public class ImageTypeDAO extends BaseDAO<ImageType, ImageTypeRepository >{
	
    public ImageType createImageType(ImageType imageType){
        return this.repo.save(imageType);
    }

    public Optional<ImageType> getImageType(String typeName){
        return this.repo.getImageTypeByTypeName(typeName);
    }

    public boolean deleteImageType(String typeName){
        this.repo.deleteByTypeName(typeName);
        return !this.repo.getImageTypeByTypeName(typeName).isPresent();
    }

}
