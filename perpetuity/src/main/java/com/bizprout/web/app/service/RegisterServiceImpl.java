package com.bizprout.web.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.RegisterService;
import com.bizprout.web.app.dto.RegisterDTO;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService<RegisterDTO> {

	@Autowired
	private BaseRepository<RegisterDTO> baseRepository;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	public void save(RegisterDTO t) {
		try {
			baseRepository.save(t);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

	}

}
