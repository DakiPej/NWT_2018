package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.Post;
import com.meminator.postmodule.Models.RegisteredUser;
import com.meminator.postmodule.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IPostRepositroy extends PagingAndSortingRepository<Post,Long> {

    List<Post> findAllByTagsIn(List<Tag> tags);
    List<Post> getPostsByUser(RegisteredUser user);
    List<Post> findAllByUserInOrderByTimeStampDesc(List<RegisteredUser> users);
}
