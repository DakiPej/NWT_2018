package com.meminator.imageModule.services;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.meminator.imageModule.dao.ImageDAO;
import com.meminator.imageModule.dao.ImageTypeDAO;
import com.meminator.imageModule.dao.PostDAO;
import com.meminator.imageModule.dao.RegisteredUserDAO;
import com.meminator.imageModule.models.Image;
import com.meminator.imageModule.models.ImageType;
import com.meminator.imageModule.models.Post;
import com.meminator.imageModule.models.RegisteredUser;

@Service
public class ImageService {
    private ImageDAO imageDAO;
    private RegisteredUserDAO registeredUserDAO;
    private ImageTypeDAO imageTypeDAO;
    private PostDAO postDAO;
    @Autowired
    public void setImageDAO(ImageDAO imageDAO){
        this.imageDAO = imageDAO;
    }

    @Autowired
    public void setRegisteredUserDAO(RegisteredUserDAO registeredUserDAO){
        this.registeredUserDAO = registeredUserDAO;
    }
    
    public String createProfilePicture(MultipartFile file,String username) throws IOException {
    	
    	ImageType imageType = imageTypeDAO.getImageType("Profile picture").get();   	
    	byte[] arrayPic = file.getBytes();
		Image profile = new Image(arrayPic,imageType);	
		Image novi = imageDAO.createImage(profile);
		Optional<RegisteredUser> user = registeredUserDAO.getUser(username);
        if(user.isPresent()){
        RegisteredUser reg = user.get();
        reg.setProfile(novi);
        registeredUserDAO.saveUser(reg);
        return "Image uploaded";
        
        }else{
            throw new IllegalArgumentException("User with given username does not exist!");
        }
    	
    }
    
   public String createMeme(MultipartFile file, Long postid) throws IOException {
    	
    	ImageType imageType = imageTypeDAO.getImageType("Meme").get();   	
    	byte[] arrayPic = file.getBytes();
		Image profile = new Image(arrayPic,imageType);	
		Image novi = imageDAO.createImage(profile);
		Optional<Post> post = postDAO.getPost(postid);
		if(post.isPresent()){
	        Post pos = post.get();
	        pos.setMeme(novi);
	        postDAO.savePost(pos);
	        return "Image uploaded";
	        
	        }else{
	            throw new IllegalArgumentException("Post with given id does not exist!");
	        }         
    }
    
    
    public Image getImage(Long id){
    	
    	
        Optional<Image> img = imageDAO.getImage(id);

        if(img.isPresent()){
            return img.get();
        }else{
            throw new IllegalArgumentException("Image with given id does not exist!");
        }
    }
    
    public Image getImageByUsername(String username){
    	Optional<RegisteredUser> user = registeredUserDAO.getUser(username);
        if(user.isPresent()){
        RegisteredUser reg = user.get();
        Image img = reg.getProfile();
        return img;
        }else{
            throw new IllegalArgumentException("Image with given username does not exist!");
        }
    }
    
    public Image getImageByPostId(Long id){
    	Optional<Post> post = postDAO.getPost(id);
        if(post.isPresent()){
        Post reg = post.get();
        Image img = reg.getMeme();
        return img;
        }else{
            throw new IllegalArgumentException("Image with given post id does not exist!");
        }
    }
    
    @Transactional
    public boolean deleteImage(Long id){
    	Optional<Image> image = imageDAO.getImage(id);

        if(image.isPresent()){
        	return imageDAO.deleteImage(id);
        }else{
            throw new IllegalArgumentException("Image  does not exist!");
        }
        
    }
    
    @Transactional
    public boolean deleteImageByUsername(String username){
    	Optional<RegisteredUser> user = registeredUserDAO.getUser(username);
        if(user.isPresent()){
        RegisteredUser reg = user.get();        
        Image img = reg.getProfile();
        reg.setProfile(null);
        registeredUserDAO.saveUser(reg);
        return imageDAO.deleteImage(img.getId());
        }else{
            throw new IllegalArgumentException("Imagefor user with given username does not exist!");
        }       
        
    }

    @Transactional
    public boolean deleteImageByPostId(Long id){
    	Optional<Post> post = postDAO.getPost(id);
        if(post.isPresent()){
        Post reg = post.get();        
        Image img = reg.getMeme();
        reg.setMeme(null);
        postDAO.savePost(reg);
        return imageDAO.deleteImage(img.getId());
        }else{
            throw new IllegalArgumentException("Image for post with given id does not exist!");
        }       
        
    }
    @Autowired
	public void setImageTypeDAO(ImageTypeDAO imageTypeDAO) {
		this.imageTypeDAO = imageTypeDAO;
	}

    @Autowired
	public void setPostDAO(PostDAO postDAO) {
		this.postDAO = postDAO;
	}
    
    

    
}
