package com.meminator.imageModule.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

import com.meminator.imageModule.models.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends PagingAndSortingRepository<RegisteredUser, Long>{

    Optional<RegisteredUser> getRegisteredUserByUsername(String username);

    void deleteByUsername(String username);

}
