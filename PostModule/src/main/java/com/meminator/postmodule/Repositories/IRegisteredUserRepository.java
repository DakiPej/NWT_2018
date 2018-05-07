package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IRegisteredUserRepository extends PagingAndSortingRepository<RegisteredUser, Long> {

    Optional<RegisteredUser> getRegisteredUserByUsername(String username);
    List<RegisteredUser> findAllByUsernameIn(List<String> usernames);
    void deleteByUsername(String username);

}
