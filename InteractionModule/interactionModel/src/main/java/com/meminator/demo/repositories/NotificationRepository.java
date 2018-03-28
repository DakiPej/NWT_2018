package com.meminator.demo.repositories;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.Notification;
import com.meminator.demo.models.RegisteredUser;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long>{
	
	public List<Notification> findByUserId(RegisteredUser userId, Pageable pageRequest);
}
