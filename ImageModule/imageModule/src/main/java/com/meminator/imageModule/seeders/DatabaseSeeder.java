package com.meminator.imageModule.seeders;

import org.jboss.logging.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.meminator.imageModule.models.Image;
import com.meminator.imageModule.models.ImageType;
import com.meminator.imageModule.repositories.ImageRepository;
import com.meminator.imageModule.repositories.ImageTypeRepository;
import com.meminator.imageModule.repositories.RegisteredUserRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder {
	
	 private Logger logger = Logger.getLogger(DatabaseSeeder.class);
	 private RegisteredUserRepository userRepository;
	 private ImageRepository imageRepository;
	 private ImageTypeRepository imageTypeRepository;
	 private JdbcTemplate jdbcTemplate;
	public DatabaseSeeder(RegisteredUserRepository userRepository, ImageRepository imageRepository,
			ImageTypeRepository imageTypeRepository, JdbcTemplate jdbcTemplate) {
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.imageTypeRepository = imageTypeRepository;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@EventListener
    public void seed(ContextRefreshedEvent event) {
        seedImageTypeTable();
        seedImageTable();
        seedRegisteredUserTable();
    }

	
	private void seedRegisteredUserTable() {
		
	}
	private void seedImageTable() {
		
        String sql = "SELECT * FROM image";
        List<Image> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
        	ImageType imageType = new ImageType((long) 1,"Meme");
        	try {
        	ClassPathResource backImgFile = new ClassPathResource("images/clapback.png");
    		byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
    		backImgFile.getInputStream().read(arrayPic);
    		Image meme = new Image(arrayPic,imageType);	
    		imageRepository.save(meme);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
        	ImageType imageType2 = new ImageType((long) 1,"Meme");
        	try {
        	ClassPathResource backImgFile2 = new ClassPathResource("images/cobra.jpg");
    		byte[] arrayPic2 = new byte[(int) backImgFile2.contentLength()];
    		backImgFile2.getInputStream().read(arrayPic2);
    		Image meme2 = new Image(arrayPic2,imageType2);	
    		imageRepository.save(meme2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
            logger.info("ImageType table seeded");
        }else {
            logger.trace("ImageType Seeding Not Required");
        }   	
   	 
	}
    private void seedImageTypeTable(){
    	
         String sql = "SELECT * FROM image_type";
         List<ImageType> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
         if(rs == null || rs.size() <= 0) {
             ImageType it = new ImageType();
             it.setTypeName("Meme");
             imageTypeRepository.save(it);
             ImageType it1 = new ImageType();
             it1.setTypeName("Profile picture");
             imageTypeRepository.save(it1);
             logger.info("ImageType table seeded");
         }else {
             logger.trace("ImageType Seeding Not Required");
         }   	
    	
    }
}
