package com.meminator.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meminator.demo.models.NotificationType;

public interface NotificationTypeRepository extends CrudRepository<NotificationType, Long>{

}
