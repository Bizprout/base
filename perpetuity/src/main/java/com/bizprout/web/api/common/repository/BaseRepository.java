package com.bizprout.web.api.common.repository;

import com.bizprout.web.app.dto.PpMasterDTO;


public interface BaseRepository<T> {

	public void save(T t);

	public T getEntity(T t);
		
	public void update(T t);

}
