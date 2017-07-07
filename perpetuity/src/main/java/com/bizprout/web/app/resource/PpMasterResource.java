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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bizprout.web.api.service.PpMasterService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;

@RestController
@RequestMapping("/ppmaster")
public class PpMasterResource {

	@Autowired
	private PpMasterService<PpMasterDTO> ppmasterservice;


	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	int sesscmpid=0;

	public PpMasterResource() {
		try {
			logger.debug(this.getClass().getSimpleName() + "Created..."+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}
	@PostMapping(value="/add", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addppmaster(@RequestBody @Valid PpMasterDTO ppmasterDTO, BindingResult result, Model model)
	{
		List<Object> jsonresponse=new ArrayList<Object>();

		if(result.hasErrors())
		{
			jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList()));
			return new ResponseEntity<Object> (jsonresponse, HttpStatus.OK);
		}
		try {
			ppmasterservice.CreatePpMaster(ppmasterDTO);
			logger.debug("Request.......addppmaster method......"+this.getClass());

			if(ppmasterDTO.getMasteridindex()>0)
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
	
	@PostMapping(value="/getppmastersnameall")
	public List<String> getPpMastersNameall(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersNameall(ppmasterDTO.getMastertype(), ppmasterDTO.getCategory(), ppmasterDTO.getCmpid());
			logger.debug("Request......getPpMastersNameall List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmaster;
	}

	@PostMapping(value="/getppmastersname")
	public List<String> getPpMastersName(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersName(ppmasterDTO.getMastertype(), ppmasterDTO.getCategory(), ppmasterDTO.getCmpid(), ppmasterDTO.getPpmastername(), ppmasterDTO.getChild());
			logger.debug("Request......getPpMastername List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmaster;
	}
	
	@PostMapping(value="/getppmasterschild")
	public List<String> getPpMastersChlid(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersChild(ppmasterDTO.getMastertype(), ppmasterDTO.getCmpid(), ppmasterDTO.getPpmastername());
			logger.debug("Request......getPpMastersChlid List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmaster;
	}
	
	@PostMapping(value="/getppmastersnamebycostcategory")
	public List<String> getPpMastersNameByCostCategory(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersNamebyCostCategory(ppmasterDTO.getMastertype(), ppmasterDTO.getCmpid(), ppmasterDTO.getPpmastername());
			logger.debug("Request......getPpMastersNameByCostCategory List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmaster;
	}
	
	@PostMapping(value="/getppmastersnamebycompany")
	public List<String> getPpMastersNameByCompany(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersNameByCompany(ppmasterDTO.getMastertype(), ppmasterDTO.getCmpid());
			logger.debug("Request......getPpMastersNameByCompany List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmaster;
	}

	@PostMapping(value="/getppparentname")
	public List<PpMasterDTO> getPpParentName(@RequestBody PpMasterDTO ppmasterDTO){

		List<PpMasterDTO> ppparentname = null;
		try {
			ppparentname=ppmasterservice.getPpParentName(ppmasterDTO.getMastertype(), ppmasterDTO.getPpmastername(), ppmasterDTO.getCmpid());
			logger.debug("Request......getPpParentName List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppparentname;
	}

	@PostMapping(value="/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> EditPpMasters(@RequestBody @Valid EditPpMasterDTO editppmasterDTO, BindingResult result, Model model)
	{
		List<Object> jsonresponse=new ArrayList<Object>();

		if(result.hasErrors())
		{
			jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList()));
			return new ResponseEntity<Object> (jsonresponse, HttpStatus.OK);
		}
		try {
			int res=ppmasterservice.UpdatePpMasters(editppmasterDTO);
			logger.debug("Request.......EditPpMasters method......"+this.getClass());

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
		return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);		
	}

	@PostMapping(value="/getppmasterdata")
	@ResponseBody
	public List<PpMasterDTO> getPpMasterData(@RequestBody PpMasterDTO ppmasterDTO)
	{
		List<PpMasterDTO> ppmasterdata = null;
		try {
			ppmasterdata=ppmasterservice.getPpMasterdata(ppmasterDTO.getCmpid(), ppmasterDTO.getMastertype());
			logger.debug("Request......getPpMasterData......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasterdata;
	}
	
	@PostMapping(value="/setsessioncmpid")
	public void setsessioncmpid(@RequestBody CompanyDTO cmpdto)
	{
		try {
			sesscmpid=cmpdto.getCmpId();
			logger.debug("Request......setsessioncmpid......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}
	
	@RequestMapping(value="/ppmasteruploadfile", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> pPmasterFileUpload(@RequestParam("file") MultipartFile file){
		
		List<Object> jsonresponse=new ArrayList<Object>();
		ExcelReadData excel= new ExcelReadData();
		boolean cmpid;
		boolean format;
		
		if(!file.isEmpty())
		{
			try {				
				
				logger.debug("inside pPmasterFileUpload method..."+this.getClass());				
				//check Excel Format
								
				format=excel.checkExcelFormatPpMasters(file);
				
				if(format==true)
				{
					//check file cmpid is same as session cmp id

					cmpid=excel.checkCmp(file, sesscmpid);
					
					if(cmpid==true)
					{
						jsonresponse=excel.excelReadData(file);
											
						if(jsonresponse.contains("success"))
						{
							jsonresponse.add("success");
						}
						else if(jsonresponse.contains("failure"))
						{
							jsonresponse.add("failure");
						}
						else
						{
							return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
						}
					}
					else
					{
						jsonresponse.add("Company Name does not match with the session Company Name!");
					}
				}
				else
				{
					jsonresponse.add("The Excel File Uploaded was of Different Format! Please Check and Upload again.");
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage()+"..."+this.getClass());
			}
		}
		else 
		{
			return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
	}
}

