package com.bizprout.web.app.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bizprout.web.api.service.RegisterService;
import com.bizprout.web.app.dto.RegisterDTO;

@Component
public class RegisterResource {

	@Autowired
	private RegisterService<RegisterDTO> registerService;

	public ResponseEntity<?> save(RegisterDTO dto) {
		registerService.save(dto);
		return null;

	}

}
