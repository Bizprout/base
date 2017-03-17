package com.bizprout.web.app.repository;

import org.springframework.stereotype.Repository;

import com.bizprout.web.api.common.BaseDTO;
import com.bizprout.web.api.common.repository.AbstractBaseRepository;

@Repository
public class BaseRepositoryImpl extends AbstractBaseRepository<BaseDTO> {

	public int getCount() {
		return 100;
	}

}
