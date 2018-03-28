package com.meminator.postmodule.DAO;

import com.meminator.postmodule.Models.Tag;
import com.meminator.postmodule.Repositories.ITagRepositroy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDAO extends BaseDAO<Tag, ITagRepositroy> {

    public Tag createTag(Tag tag){
        return this.repo.save(tag);
    }

    public List<Tag> getTags(){
        return (List<Tag>) this.repo.findAll();
    }

    public Optional<Tag> getTag(Long id){
        return this.repo.findById(id);
    }

    public Optional<Tag> getByName(String name){
        return this.repo.getByName(name);
    }

}
