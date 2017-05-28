package com.bizprout.web.app.resource;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.UserCounterService;
import com.bizprout.web.app.dto.UserCounterDTO;

@RestController
@RequestMapping("/usercounter")
public class UserCounterResource {

	@Autowired
	private UserCounterService<UserCounterDTO> usercounterservice;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public UserCounterResource() {
		
		logger.debug(this.getClass().getSimpleName() + "Created..."+this.getClass());
	}
	
	@PostMapping(value="/insert")
	public void insertUserCounter(@RequestBody @Valid UserCounterDTO usercounterdto, BindingResult result, Model model)
	{		
		try {
			usercounterservice.insertusercounter(usercounterdto);
			logger.info("Request.......insertusercounter method......"+this.getClass());
			
		} catch (Exception e) {

			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}
	
	@PostMapping(value="/getlastlogindatetime")
	public UserCounterDTO getLastLoginDateTime(@RequestBody UserCounterDTO usercounterdto)
	{
		UserCounterDTO usercounter=null;
		try {
			usercounter=usercounterservice.getlastlogindatetime(usercounterdto.getUserid());
			logger.info("Request.......getLastLoginDateTime method......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usercounter;
	}
}
