package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepositroy extends JpaRepository<Post,Long>{
}
