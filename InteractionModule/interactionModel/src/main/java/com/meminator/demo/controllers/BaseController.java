package com.meminator.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class BaseController <M, R extends CrudRepository<M, Long>>{
	
	protected R repo;
	
	@Autowired
	public void setRepo (R repository)	{
		repo = repository; 
	}
	
	@RequestMapping("/create")
	public M Create (@RequestBody M model)	{
		return repo.save(model); 
	}
}
