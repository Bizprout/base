package com.bizprout.web.app.resource;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.CompanyService;
import com.bizprout.web.app.dto.CompanyDTO;

@RestController
@RequestMapping("/company")
public class CompanyResource {
	
	@Autowired
	private CompanyService<CompanyDTO> companyservice;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public CompanyResource() {	
		try {
			System.out.println(this.getClass().getSimpleName() + "Created...");
			logger.debug(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping(value="/getclientstatus")
	public CompanyDTO getClientStatus(@RequestBody CompanyDTO companyDTO)
	{
		CompanyDTO compDTO = null;
		try {
			logger.debug("Request......getClientStatus......");
			
			compDTO=companyservice.getClientStatus(companyDTO.getTallyCmpName());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return compDTO;
	}
	
	@PostMapping(value="/update", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateCompany(@RequestBody CompanyDTO companydto)
	{
		ResponseEntity<String> resp = null;
		try {
			int result=companyservice.updateCompany(companydto);
			logger.debug("Request.......updateCompany method......");
			
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
	
	@GetMapping(value="/getcompanydata")
	@ResponseBody
	public List<CompanyDTO> getCompanyData()
	{
		List<CompanyDTO> compdto = null;
		try {
			compdto=companyservice.getCompanyData();
			logger.debug("Request......getCompanyData......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compdto;
	}
	
	@PostMapping(value="/updatecompanystatus", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateCompanyStatus(@RequestBody CompanyDTO companydto)
	{
		ResponseEntity<String> resp = null;
		try {
			int result=companyservice.updateCompanyStatus(companydto);
			logger.debug("Request.......updateCompanyStatus method......");
			
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

}
