package com.meminator.postmodule.Repositories;

import com.meminator.postmodule.Models.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
}
