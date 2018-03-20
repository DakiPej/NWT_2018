package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
}
