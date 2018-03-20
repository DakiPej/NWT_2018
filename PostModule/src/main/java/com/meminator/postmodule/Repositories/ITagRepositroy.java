package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagRepositroy extends JpaRepository<Tag,Long>{
}
