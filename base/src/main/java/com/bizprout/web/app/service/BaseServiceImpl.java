package com.bizprout.web.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.BaseDTO;
import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.BaseService;

@Service
public class BaseServiceImpl implements BaseService<BaseDTO> {

	@Autowired
	private BaseRepository<BaseDTO> baseRepository;

	public void testService(BaseDTO baseDTO) {
		System.out.println("test inside service" + baseDTO);
		baseRepository.save(baseDTO);

	}

	public BaseDTO auth(BaseDTO baseDTO) {
		System.out.println("inside auth method...");
		BaseDTO fromDb = this.baseRepository.getEntity(baseDTO);
		System.out.println(fromDb);
		return fromDb;

	}

}
