package com.bizprout.web.app.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.UserMappingService;
import com.bizprout.web.app.dto.CompanyDTO;

@RestController
@RequestMapping("/usermapping")
public class UserMappingResource {
	
	@Autowired
	private UserMappingService<CompanyDTO> usermappingservice;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(value="/getcompanylist")
	@ResponseBody
	public List<String> getCompanyList()
	{
		List<String> cdto = null;
		try {
			cdto=usermappingservice.getCompanyList();
			logger.debug("Request......getCompanyList......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cdto;
	}

}
