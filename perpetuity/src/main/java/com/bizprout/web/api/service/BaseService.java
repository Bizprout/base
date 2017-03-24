package com.bizprout.web.api.service;


public interface BaseService<T> {

	public void testService(T t);
	
	public void updateservice(T t);

	public T auth(T t);
	

}
