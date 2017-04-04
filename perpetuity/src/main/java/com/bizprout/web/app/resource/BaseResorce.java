package com.bizprout.web.app.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
			System.out.println(this.getClass().getSimpleName() + "Created...");
			logger.info(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PostMapping(value = "/call")
	public ResponseEntity<BaseDTO> testGet(@RequestBody BaseDTO baseDTO) {
		try {
			baseService.testService(baseDTO);
			logger.info("Request.......testGet method......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<BaseDTO>(baseDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/auth")
	public ResponseEntity auth(@RequestBody BaseDTO baseDTO) {
		ResponseEntity resp = null;
		try {
			logger.info("Request........auth method......");
			if (baseService.auth(baseDTO) != null) {
				resp = new ResponseEntity<BaseDTO>(HttpStatus.OK);
			}
			resp = new ResponseEntity<String>("Bad Credentials",
					HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;

	}
	
	

}
