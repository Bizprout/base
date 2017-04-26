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
			System.out.println("test inside service" + baseDTO);
			logger.info("test inside service" + baseDTO);
			baseRepository.save(baseDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BaseDTO auth(BaseDTO baseDTO) {
		BaseDTO fromDb = null;
		try {
			System.out.println("inside auth method...");
			logger.info("inside auth method...");
			
			fromDb= this.baseRepository.getEntity(baseDTO);
			System.out.println(fromDb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fromDb;
	}

	public int updateservice(BaseDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}



}
