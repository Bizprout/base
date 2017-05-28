package com.bizprout.web.app.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BaseService<BaseDTO> baseService;

	public BaseResorce() {
		try {
			logger.info(this.getClass().getSimpleName() + "Created..."+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}

	@PostMapping(value = "/call")
	public ResponseEntity<BaseDTO> testGet(@RequestBody BaseDTO baseDTO) {
		try {
			baseService.testService(baseDTO);
			logger.info("Request.......testGet method......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return new ResponseEntity<BaseDTO>(baseDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/auth")
	public ResponseEntity<Object> auth(@RequestBody BaseDTO baseDTO) {
		ResponseEntity<Object> resp = null;
		try {
			logger.info("Request........auth method......"+this.getClass());
			if (baseService.auth(baseDTO) != null) {
				resp = new ResponseEntity<Object>(HttpStatus.OK);
			}
			resp = new ResponseEntity<Object>("Bad Credentials",
					HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return resp;

	}
	
	

}
