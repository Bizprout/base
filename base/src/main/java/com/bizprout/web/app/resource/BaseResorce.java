package com.bizprout.web.app.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.common.BaseDTO;
import com.bizprout.web.api.service.BaseService;

@RestController
@RequestMapping("/test")
public class BaseResorce {

	@Autowired
	private BaseService<BaseDTO> baseService;

	public BaseResorce() {
		System.out.println(this.getClass().getSimpleName() + "Created...");
	}

	@PostMapping(value = "/call")
	public ResponseEntity<BaseDTO> testGet(@RequestBody BaseDTO baseDTO) {
		baseService.testService(baseDTO);
		return new ResponseEntity<BaseDTO>(baseDTO, HttpStatus.OK);

	}

	@PostMapping(value = "/auth")
	public ResponseEntity auth(@RequestBody BaseDTO baseDTO) {
		if (baseService.auth(baseDTO) != null) {
			return new ResponseEntity<BaseDTO>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("Bad Credentials",
				HttpStatus.FORBIDDEN);

	}

}
