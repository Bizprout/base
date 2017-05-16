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
import com.bizprout.web.app.dto.UserDTO;

@RestController
@RequestMapping("/login")
public class LoginResource {

	@Autowired
	private LoginService<UserDTO> loginService;

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

	@PostMapping(value="/authe")
	public ResponseEntity<Object> authenticate(@RequestBody LoginVO loginVO)
	{	
		ResponseEntity<Object> resp = null;
		try {
			logger.debug("before calling authenticate with details {}",loginVO);
			logger.info("calling authenticate with details {}",loginVO.getUsername());
			UserDTO userDTO=loginService.authenticate(loginVO);
			String password=AESencrp.decrypt(userDTO.getPassword());
			userDTO.setPassword(password);
			logger.debug("after service call in  authenticate with details {}",userDTO);

			logger.debug("Checking if the DTO is not null",userDTO);
			logger.debug("Sending HTTP status 200 - ok",userDTO);
			resp= new ResponseEntity<Object>(userDTO, HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

}
