package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepositroy extends JpaRepository<Post,Long>{
}
