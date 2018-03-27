package com.meminator.imageModule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meminator.imageModule.models.ImageType;
import com.meminator.imageModule.services.ImageTypeService;


@RestController
@RequestMapping(path = "/imgtype")
public class ImageTypeController {
	
	 private ImageTypeService imageTypeService;

	    @Autowired
	    public void setRegisteredUserService(ImageTypeService imageTypeService){
	        this.imageTypeService = imageTypeService;
	    }

	    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	    public ResponseEntity createImageType(@RequestBody ImageType imageType){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(imageTypeService.createImageType(imageType));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
	        }
	    }

	    @RequestMapping(method = RequestMethod.GET, value = "/{typename}")
	    public ResponseEntity getImageType(@PathVariable String typeName){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(imageTypeService.getImageType(typeName));
	        }catch (IllegalArgumentException e){
	            return ResponseEntity.status(404).body(e.getLocalizedMessage());
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
	        }
	    }
	    @RequestMapping(method = RequestMethod.DELETE, value = "/{typename}")
	    public ResponseEntity deleteImageType(@PathVariable String typeName){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(imageTypeService.deleteImageType(typeName));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
	        }
	    }

}
