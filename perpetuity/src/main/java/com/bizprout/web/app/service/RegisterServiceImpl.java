package com.bizprout.web.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.RegisterService;
import com.bizprout.web.app.dto.RegisterDTO;

@Service
public class RegisterServiceImpl implements RegisterService<RegisterDTO> {

	@Autowired
	private BaseRepository<RegisterDTO> baseRepository;

	public void save(RegisterDTO t) {
		try {
			baseRepository.save(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
