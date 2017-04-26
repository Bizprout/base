package com.bizprout.web.api.service;


public interface BaseService<T> {

	public void testService(T t);
	
	public int updateservice(T t);

	public T auth(T t);
	

}
