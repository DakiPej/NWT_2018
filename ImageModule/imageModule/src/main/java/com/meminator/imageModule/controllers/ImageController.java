package com.meminator.imageModule.controllers;



import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meminator.imageModule.models.Image;
import com.meminator.imageModule.models.ImageType;
import com.meminator.imageModule.services.ImageService;



@RestController
@RequestMapping(path = "/images")
public class ImageController {
	private ImageService imageService;

    @Autowired
    public void setRegisteredUserService(ImageService imageService){
        this.imageService = imageService;
    }
	
	@PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.POST, value="/upload/profile")
    public ResponseEntity createProfilePicture(MultipartHttpServletRequest request, OAuth2Authentication authentication){
        
    	try{   
    		Iterator<String> itr = request.getFileNames();
    		 while (itr.hasNext()) {
                 String uploadedFile = itr.next();
                 MultipartFile file = request.getFile(uploadedFile);
                 return ResponseEntity.status(HttpStatus.OK).body(imageService.createProfilePicture(file, authentication.getName()));
    		 }
    	        return new ResponseEntity<>("{Prazno}", HttpStatus.OK);
    		 }catch (Exception e){
 	        	System.out.println(e.getMessage().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.POST, value="/upload/meme")
    public ResponseEntity<Long> createMeme(MultipartHttpServletRequest request){
        
    	try{   
    		Iterator<String> itr = request.getFileNames();
    		 while (itr.hasNext()) {
                 String uploadedFile = itr.next();
                 MultipartFile file = request.getFile(uploadedFile);
                 return ResponseEntity.status(HttpStatus.OK).body(imageService.createMeme(file));
    		 }
    	        return (ResponseEntity<Long>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
    		 }catch (Exception e){
            return (ResponseEntity<Long>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }
	
	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", 
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
    	
    	 try{
    		 Image image = imageService.getImage(id);
    		 byte[] bytes = image.getData();

    	        return ResponseEntity
    	        		.status(HttpStatus.OK)
    	                .contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);

	        }catch (IllegalArgumentException e){
	        	byte[] bytes = null;
	            return  ResponseEntity.status(404).contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);
	        }catch (Exception e){
	        	byte[] bytes = null;
	            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);
	        }
    	
        
    }
	
	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET, value = "/profile/{username}", 
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageByUsername(@PathVariable String username) throws IOException {
    	
    	 try{
    		 Image image = imageService.getImageByUsername(username);
    		 byte[] bytes = image.getData();

    	        return ResponseEntity
    	        		.status(HttpStatus.OK)
    	                .contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);

	        }catch (IllegalArgumentException e){
	        	byte[] bytes = null;
	            return  ResponseEntity.status(404).contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);
	        }catch (Exception e){
	        	byte[] bytes = null;
	            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);
	        }
    	
        
    }
	
	@PreAuthorize("isAnonymous() or hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.GET, value = "/meme/{id}", 
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageByPostId(@PathVariable Long id) throws IOException {
    	
    	 try{
    		 Image image = imageService.getImageByPostId(id);
    		 byte[] bytes = image.getData();

    	        return ResponseEntity
    	        		.status(HttpStatus.OK)
    	                .contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);

	        }catch (IllegalArgumentException e){
	        	byte[] bytes = null;
	            return  ResponseEntity.status(404).contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);
	        }catch (Exception e){
	        	System.out.println(e.getMessage().toString());
	        	byte[] bytes = null;
	            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.IMAGE_JPEG)
    	                .body(bytes);
	        }
    	
        
    }
	
	@PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity deleteImage(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(imageService.deleteImage(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
	
	@PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/profile")
    public ResponseEntity deleteImageByUsername(OAuth2Authentication authentication){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(imageService.deleteImageByUsername(authentication.getName()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
	}
	
	@PreAuthorize("hasRole('ROLE_user')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/meme/{id}")
    public ResponseEntity deleteImageByPostId(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(imageService.deleteImageByPostId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
    
}
