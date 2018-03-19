package com.meminator.imageModule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
}