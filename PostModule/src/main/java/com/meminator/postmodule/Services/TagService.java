package com.meminator.postmodule.Services;

import com.meminator.postmodule.DAO.TagDAO;
import com.meminator.postmodule.Models.Tag;
import com.meminator.postmodule.Repositories.ITagRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private TagDAO tagDAO;

    @Autowired
    public void setTagDAO(TagDAO tagDAO){
        this.tagDAO = tagDAO;
    }

    public Tag createTag(Tag tag){
        return tagDAO.createTag(tag);
    }

    public List<Tag> getTags(){
        return tagDAO.getTags();
    }

    public Tag getTag(Long id){

        Optional<Tag> newTag = tagDAO.getTag(id);

        if(newTag.isPresent()){
            return  newTag.get();
        }else throw new IllegalArgumentException("Tag not found!");

    }

    public Tag getTagByName(String name){

        Optional<Tag> newTag = tagDAO.getByName(name);

        if(newTag.isPresent()){
            return  newTag.get();
        }else throw new IllegalArgumentException("Tag not found!");

    }



}
