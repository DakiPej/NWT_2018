package com.meminator.imageModule.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.meminator.imageModule.models.ImageType;

@Repository
public interface ImageTypeRepository extends PagingAndSortingRepository<ImageType, Long>{
	Optional<ImageType> getImageTypeByTypeName(String typeName);
	void deleteByTypeName(String typeName);
}