package com.meminator.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meminator.demo.models.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long>{

}
