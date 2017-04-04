package com.bizprout.web.app.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.common.BaseDTO;
import com.bizprout.web.api.service.BaseService;
import com.bizprout.web.api.service.LoginService;
import com.bizprout.web.app.dto.LoginDTO;
import com.bizprout.web.app.dto.LoginVO;

@RestController
@RequestMapping("/login")
public class LoginResource {
	
	@Autowired
	private LoginService<LoginDTO> loginService;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public LoginResource()
	{
		try {
			System.out.println(this.getClass().getSimpleName() + "Created...");
			logger.info(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/authe")
	public ResponseEntity authenticate(@RequestBody LoginVO loginVO)
	{	
		ResponseEntity resp = null;
		try {
			logger.debug("before calling authenticate with details {}",loginVO);
			logger.info("calling authenticate with details {}",loginVO.getUsername());
			LoginDTO loginDTO=loginService.authenticate(loginVO);
			logger.debug("after service call in  authenticate with details {}",loginDTO);
			
			if(loginService.authenticate(loginVO)!=null)
			{
				logger.debug("Checking if the DTO is not null",loginDTO);
				logger.debug("Sending HTTP status 200 - ok",loginDTO);
				resp= new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
			}
			else
			{
				logger.debug("Sending HTTP status - Forbidden",loginDTO);
				logger.error("Error: Forbidden Access",loginDTO);
				resp= new ResponseEntity<String>("Error1!", HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

}
