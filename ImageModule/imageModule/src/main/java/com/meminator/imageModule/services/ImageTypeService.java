package com.meminator.imageModule.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meminator.imageModule.dao.ImageTypeDAO;
import com.meminator.imageModule.models.ImageType;


@Service
public class ImageTypeService {
	private ImageTypeDAO imageTypeDAO;

    @Autowired
    public void setImageTypeDAO(ImageTypeDAO imageTypeDAO){
        this.imageTypeDAO = imageTypeDAO;
    }

    public ImageType createImageType(ImageType imageType){
        return imageTypeDAO.createImageType(imageType);
    }

    public ImageType getImageType(String typeName){

        Optional<ImageType> imageType = imageTypeDAO.getImageType(typeName);

        if(imageType.isPresent()){
            return imageType.get();
        }else{
            throw new IllegalArgumentException("Image type does not exist!");
        }

    }
    @Transactional 
    public boolean deleteImageType(String typeName){
    	Optional<ImageType> imageType = imageTypeDAO.getImageType(typeName);

        if(imageType.isPresent()){
        	return imageTypeDAO.deleteImageType(typeName);
        }else{
            throw new IllegalArgumentException("Image type does not exist!");
        }
        
    }

}
