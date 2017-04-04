package com.bizprout.web.app.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.BaseService;
import com.bizprout.web.api.service.UserService;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.dto.UserEditVO;
import com.bizprout.web.app.dto.UserVO;


@RestController
@RequestMapping("/user")
public class UserResource {
	
	@Autowired
	private UserService<UserDTO> userservice;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public UserResource()
	{
		try {
			System.out.println(this.getClass().getSimpleName() + "Created...");
			logger.debug(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping(value="/add", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity adduser(@RequestBody UserDTO userDTO)
	{
		ResponseEntity resp = null;
		try {
			userservice.CreateUser(userDTO);
			logger.debug("Request.......adduser method......");
			
			if(userDTO.getUserid()>0)
			{
				resp= new ResponseEntity<String>("success", HttpStatus.OK);
			}
			else
			{
				resp= new ResponseEntity<String>("failure", HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return resp;
	}
	
	@PostMapping(value="/edit", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity edituser(@RequestBody UserEditVO usereditVO)
	{
		ResponseEntity resp = null;
		try {
			int result=userservice.UpdateUser(usereditVO);
			logger.debug("Request.......Edit user method......");
			
			if(result>0)
			{
				resp= new ResponseEntity<String>("success", HttpStatus.OK);
			}
			else
			{
				resp= new ResponseEntity<String>("failure", HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;		
	}
	
	@GetMapping(value="/getusernames")
	@ResponseBody
	public List<String> getUsers()
	{
		List<String> udto = null;
		try {
			udto=userservice.getUsernameList();
			logger.debug("Request......getUsernameList......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return udto;
	}
	
	@GetMapping(value="/getusersreport")
	@ResponseBody
	public List<UserDTO> getUsersReport()
	{
		List<UserDTO> udto = null;
		try {
			udto=userservice.getUsers();
			logger.debug("Request......getUsersreport......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return udto;
	}
	
	@PostMapping(value="/getuserdata")
	public ResponseEntity<UserDTO> getUserData(@RequestBody UserVO uservo)
	{
		ResponseEntity resp = null;
		try {
			logger.debug("Request......getUserData......");
			
			UserDTO userdto=userservice.getUserData(uservo);
			resp = new ResponseEntity<UserDTO>(userdto, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

}
