package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ITagRepositroy extends PagingAndSortingRepository<Tag,Long> {

    Optional<Tag> getByName(String name);

}
