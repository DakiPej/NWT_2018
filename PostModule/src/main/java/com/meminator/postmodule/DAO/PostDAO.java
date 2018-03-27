package com.meminator.postmodule.DAO;

import com.meminator.postmodule.Models.Post;
import com.meminator.postmodule.Models.RegisteredUser;
import com.meminator.postmodule.Models.Tag;
import com.meminator.postmodule.Repositories.IPostRepositroy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostDAO extends BaseDAO<Post, IPostRepositroy> {

    public Post savePost(Post post){
        return this.repo.save(post);
    }

    public List<Post> getPosts(){
        return (List<Post>) this.repo.findAll();
    }

    public Optional<Post> getPost(Long id){
        return this.repo.findById(id);
    }

    public List<Post> getPostsByTags(List<Tag> tags){
        return this.repo.findAllByTagsIn(tags);
    }

    public List<Post> getPostsByUser(RegisteredUser user){
        return this.repo.getPostsByUser(user);
    }

    public boolean deletePost(Long id){
        this.repo.deleteById(id);
        return !this.repo.findById(id).isPresent();
    }

}
