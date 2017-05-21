package com.bizprout.web.api.common.repository;


public interface BaseRepository<T> {

	public int save(T t);

	public T getEntity(T t);
		
	public void update(T t);

}
