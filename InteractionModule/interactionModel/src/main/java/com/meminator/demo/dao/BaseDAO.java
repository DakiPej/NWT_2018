package com.meminator.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class BaseDAO <M, R extends PagingAndSortingRepository<M, Long>>{
	
	protected R repo; 
	
	@Autowired
	public void setRepo (R repository)	{
		repo = repository;
	}
	
	protected M create (M model)	{
		return repo.save(model); 
	}
}
