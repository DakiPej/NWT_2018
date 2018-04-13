package com.meminator.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.NotificationType;

public interface NotificationTypeRepository extends PagingAndSortingRepository<NotificationType, Long>{
	
	NotificationType findBytypeName(String typeName);
}
