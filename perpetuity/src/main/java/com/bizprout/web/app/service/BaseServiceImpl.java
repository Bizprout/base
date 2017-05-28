package com.bizprout.web.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.BaseDTO;
import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.BaseService;

@Service
public class BaseServiceImpl implements BaseService<BaseDTO> {

	@Autowired
	private BaseRepository<BaseDTO> baseRepository;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());


	public void testService(BaseDTO baseDTO) {
		try {
			logger.info("test inside service" + baseDTO, this.getClass());
			baseRepository.save(baseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

	}

	public BaseDTO auth(BaseDTO baseDTO) {
		BaseDTO fromDb = null;
		try {
			logger.info("inside auth method..."+this.getClass());
			
			fromDb= this.baseRepository.getEntity(baseDTO);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return fromDb;
	}

	public int updateservice(BaseDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}



}
