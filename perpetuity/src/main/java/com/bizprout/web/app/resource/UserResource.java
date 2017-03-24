package com.bizprout.web.app.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.BaseService;
import com.bizprout.web.app.dto.UserDTO;


@RestController
@RequestMapping("/user")
public class UserResource {
	
	@Autowired
	private BaseService<UserDTO> baseService;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public UserResource()
	{
		System.out.println(this.getClass().getSimpleName() + "Created...");
		logger.info(this.getClass().getSimpleName() + "Created...");
	}
	
	@PostMapping(value="/add", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity adduser(@RequestBody UserDTO userDTO)
	{
		baseService.testService(userDTO);
		logger.info("Request.......adduser method......");
		
		if(userDTO.getUserid()>0)
		{
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("failure", HttpStatus.OK);
		}		
	}
	
	@PostMapping(value="/edit", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity edituser(@RequestBody UserDTO userDTO)
	{
		baseService.updateservice(userDTO);
		logger.info("Request.......Edit user method......");
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
		
	}

}
