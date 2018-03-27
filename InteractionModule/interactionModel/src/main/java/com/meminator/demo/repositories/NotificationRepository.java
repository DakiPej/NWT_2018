package com.meminator.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.meminator.demo.models.Notification;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long>{

}
