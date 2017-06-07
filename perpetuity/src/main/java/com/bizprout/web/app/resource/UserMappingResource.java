package com.bizprout.web.app.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.UserMappingService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.ScreensDTO;
import com.bizprout.web.app.dto.UserMappingDTO;

@RestController
@RequestMapping("/usermapping")
public class UserMappingResource {

	@Autowired
	private UserMappingService<CompanyDTO> usermappingservice;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	public UserMappingResource() {
		logger.debug("UserMapping Resource Created.."+this.getClass());
	}

	@GetMapping(value="/getcompanylist")
	@ResponseBody
	public List<String> getCompanyList()
	{
		List<String> cdto = null;
		try {
			cdto=usermappingservice.getCompanyList();
			logger.debug("Request......getCompanyList......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return cdto;
	}

	@GetMapping(value="/getscreens")
	@ResponseBody
	public List<ScreensDTO> getScreens()
	{
		List<ScreensDTO> udto = null;
		try {
			udto=usermappingservice.getScreensList();
			logger.debug("Request......getScreens......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return udto;
	}

	@PostMapping(value="/updateUserMapping")
	public ResponseEntity<Object> updateUserMapping(@RequestBody @Valid UserMappingDTO userMapDto,BindingResult result,Model model)
	{
		List<Object> jsonresponse = new ArrayList<Object>();

	    if(result.hasErrors())
	    {
	      jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
	          .collect(Collectors.toList()));
	      return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
	    }
	    
		try {			
			
			int res = usermappingservice.updateUserMapping(userMapDto);
			
			logger.debug("update User Mapping method......"+this.getClass());

			if(res>0)
			{
				jsonresponse.add("success");
			}
			else
			{
				jsonresponse.add("failure");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return new ResponseEntity<Object>(jsonresponse,HttpStatus.OK);		
	}
	
	@GetMapping(value="/getUserScreens")
	@ResponseBody
	public List<UserMappingDTO> getUserScreens()
	{
		List<UserMappingDTO> udto = null;
		try {
			udto=usermappingservice.getUserMapData();
			logger.debug("Request......getUserScreens......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return udto;
	}
	
	@PostMapping(value="/getScreensMapped")
	public UserMappingDTO getUserMapByCmpUser(@RequestBody UserMappingDTO userMapDto)
	{
		UserMappingDTO usermapdto=null;
		try {
			usermapdto=usermappingservice.getUserMapByCmpUser(userMapDto.getCmpId(), userMapDto.getUserid());
			logger.info("Request.......getUserMapByCmpUser method......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usermapdto;
	}
	
	@PostMapping(value="/getScreensMappedList")
	public List<UserMappingDTO> getUserMapByCmpUserList(@RequestBody UserMappingDTO userMapDto)
	{
		List<UserMappingDTO> usermapdto=null;
		try {
			usermapdto=usermappingservice.getUserMapByCmpUserList(userMapDto.getCmpId(), userMapDto.getUserid());
			logger.info("Request.......getUserMapByCmpUser method......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usermapdto;
	}
	
	@PostMapping(value="/getCompaniesMapped")
	public List<UserMappingDTO> getCmpByuserid(@RequestBody UserMappingDTO userMapDto)
	{
		List<UserMappingDTO> usermapdto=null;
		try {
			usermapdto=usermappingservice.getCmpByuserid(userMapDto.getUserid());
			logger.info("Request.......getCmpByuserid method......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usermapdto;
	}
	
	@PostMapping(value="/getScreensMappedByCmpid")
	public List<UserMappingDTO> getScreensMappedByCmpid(@RequestBody UserMappingDTO userMapDto)
	{
		List<UserMappingDTO> usermapdto=null;
		try {
			usermapdto=usermappingservice.getScreensMappedByCmpid(userMapDto.getCmpId());
			logger.info("Request.......getCmpByuserid method......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return usermapdto;
	}
	
	@PostMapping(value="/deleteScreensByCmpidUserid")
	public ResponseEntity<String> deleteScreensByCmpidUserid(@RequestBody UserMappingDTO userMapDto)
	{
		ResponseEntity<String> resp=null;
		int result=0;
		try {
			result=usermappingservice.deleteScreensByCmpidUserid(userMapDto.getCmpId(), userMapDto.getUserid());
			logger.info("Request.......deleteScreensByCmpidUserid method......"+this.getClass());
			
			if(result>0)
			{
				resp=new ResponseEntity<String>("success", HttpStatus.OK);
			}
			else
			{
				resp=new ResponseEntity<String>("failure", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return resp;
	}

}
