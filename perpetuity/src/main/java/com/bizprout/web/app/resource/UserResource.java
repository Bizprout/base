package com.bizprout.web.app.resource;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> adduser(@RequestBody @Valid UserDTO userDTO, BindingResult result, Model model)
	{
		List<Object> jsonresponse = new ArrayList<Object>();

		if(result.hasErrors())
		{
			jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList()));
			return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
		}

		try {

			//for password auto generation - 10 char

			UUID uuid = UUID.randomUUID();
			long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();

			String password=AESencrp.encrypt(Long.toString(l, Character.MAX_RADIX).subSequence(0, 9).toString());

			userDTO.setPassword(password);

			userservice.CreateUser(userDTO);
			logger.debug("Request.......adduser method......");


			if(userDTO.getUserid()>0)
			{
				jsonresponse.add("success");

				String decryptedpassword=AESencrp.decrypt(userDTO.getPassword());

				Email email=new Email();
				email.sendEmail(userDTO.getEmailid(), userDTO.getUsername(), decryptedpassword);			
			}
			else
			{
				jsonresponse.add("failure");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
	}

	@PostMapping(value="/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> edituser(@RequestBody @Valid UserEditVO usereditVO, BindingResult result, Model model)
	{
		List<Object> jsonresponse = new ArrayList<Object>();
		try {
			int res=userservice.UpdateUser(usereditVO);
			logger.debug("Request.......Edit user method......");


			if(result.hasErrors())
			{
				jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
						.collect(Collectors.toList()));
			}
			else if(res>0)
			{
				jsonresponse.add("success");
			}
			else
			{
				jsonresponse.add("failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object> (jsonresponse, HttpStatus.OK);		
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
		ResponseEntity<UserDTO> resp = null;
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
