package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IRegisteredUserRepository extends PagingAndSortingRepository<RegisteredUser, Long> {

    Optional<RegisteredUser> getRegisteredUserByUsername(String username);

    void deleteByUsername(String username);
}
