package com.bizprout.web.app.resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

import com.bizprout.web.api.service.TallyMappingService;
import com.bizprout.web.app.dto.CompanyDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.TallyMappingDTO;
import com.bizprout.web.app.dto.TallyMastersDTO;


@RestController
@RequestMapping("/pptallymapping")
public class TallyMappingResource {

	@Autowired
	private TallyMappingService<TallyMappingDTO> tallymappingservice;

	Logger logger=LoggerFactory.getLogger(this.getClass());

	int sesscmpid=0;

	public TallyMappingResource() {
		try {
			logger.info(this.getClass().getSimpleName() + "Created..."+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}

	@PostMapping(value="/getcompanyidname")
	public List<CompanyDTO> getCompanyIdName(@RequestBody CompanyDTO compdto)
	{
		List<CompanyDTO> cdto = null;
		try {
			cdto=tallymappingservice.getCompanyIdName(compdto.getClientId());
			logger.debug("Request......getCompanyIdName List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return cdto;
	}

	@PostMapping(value="/gettallymasternames")
	public List<TallyMastersDTO> getTallyMasterNames(@RequestBody TallyMastersDTO tallymasterdto)
	{
		List<TallyMastersDTO> tallymasters = null;
		try {
			tallymasters=tallymappingservice.getTallyMasterNames(tallymasterdto.getMasterType(), tallymasterdto.getCmpId());
			logger.debug("Request......getTallyMasterNames List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return tallymasters;
	}

	@PostMapping(value="/getppmasternames")
	public List<PpMasterDTO> getPpMasterNames(@RequestBody PpMasterDTO ppmasterdto)
	{
		List<PpMasterDTO> ppmasternames = null;
		try {
			ppmasternames=tallymappingservice.getPpMasterNames(ppmasterdto.getMastertype(), ppmasterdto.getCmpid());
			logger.debug("Request......getTallyMasterNames List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasternames;
	}

	@PostMapping(value="/insert", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertTallyMapping(@RequestBody @Valid TallyMappingDTO tallymappingdto, BindingResult result, Model model)
	{
		List<Object> jsonresponse=new ArrayList<Object>();

		if(result.hasErrors())
		{
			jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList()));
			return new ResponseEntity<Object> (jsonresponse, HttpStatus.OK);
		}

		try {
			logger.info("inside insertTallyMapping method....."+this.getClass());
			tallymappingservice.insertTallyMapping(tallymappingdto);

			if(tallymappingdto.getMappingId()!=0)
			{
				jsonresponse.add("success");
			}
			else
			{
				jsonresponse.add("failure");
			}
		} catch (Exception e) {
			logger.error("Exception in insertTallyMapping method \t" + e.getMessage(), this.getClass());
		}

		return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
	}

	@PostMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateTallyMapping(@RequestBody @Valid TallyMappingDTO tallymappingdto, BindingResult result, Model model)
	{
		List<Object> jsonresponse=new ArrayList<Object>();

		if(result.hasErrors())
		{
			jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList()));
			return new ResponseEntity<Object> (jsonresponse, HttpStatus.OK);
		}

		int res=0;
		try {
			logger.info("inside updateTallyMapping method..."+this.getClass());
			res=tallymappingservice.updateTallyMapping(tallymappingdto);

			if(res>0)
			{
				jsonresponse.add("success");
			}
			else
			{
				jsonresponse.add("failure");
			}
		} catch (Exception e) {
			logger.error("Exception in updateTallyMapping method \t" + e.getMessage(), this.getClass());
		}

		return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
	}

	@PostMapping(value="/gettallymasterids")
	public List<Integer> getPpMastersMapping(@RequestBody TallyMappingDTO tallymappingdto)
	{
		List<Integer> ppmapping = null;
		try {
			ppmapping=tallymappingservice.getPpMastersMapping(tallymappingdto.getCmpId(), tallymappingdto.getPpId());
			logger.debug("Request......getTallyMasterids List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmapping;
	}

	@PostMapping(value="/deleteppidcmpid")
	public int deletePpidCmpid(@RequestBody TallyMappingDTO tallymappingdto)
	{
		int res=0;
		try {
			res=tallymappingservice.deletePpidCmpid(tallymappingdto.getCmpId(), tallymappingdto.getPpId());
			logger.debug("Request......deletePpidCmpid List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return res;
	}

	@PostMapping(value="/gettallyppmappingdata")
	public List<TallyMastersDTO> getPpMasterData(@RequestBody TallyMastersDTO tallymasterdto)
	{
		List<TallyMastersDTO> tallymasterdata = null;
		try {
			tallymasterdata=tallymappingservice.getTallyPpMappingData(tallymasterdto.getCmpId(), tallymasterdto.getMasterType());
			logger.debug("Request......getPpMasterData......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return tallymasterdata;
	}


	@PostMapping(value="/saveppmapping", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updatePpMasterMapping(@RequestBody List<TallyMastersDTO> tallymasterdto)
	{
		int result=0;
		ResponseEntity<String> resp = null;
		try {
			
			logger.debug("Request......updatePpMasterMapping......"+this.getClass());

			result=tallymappingservice.savePpMasterMapping(tallymasterdto);

			if(result>0)
			{
				resp= new ResponseEntity<String>("success", HttpStatus.OK);
			}
			else
			{
				resp= new ResponseEntity<String>("failure", HttpStatus.OK);
			}

			logger.debug("Request......getPpMasterData......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return resp;
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

	@RequestMapping(value="/ppmastermappinguploadfile", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> pPmastermappingFileUpload(@RequestParam("file") MultipartFile file){

		List<Object> jsonresponse=new ArrayList<Object>();

		ExcelReadData excel= new ExcelReadData();
		boolean cmpid;
		boolean format;
		
		logger.debug("Request......pPmastermappingFileUpload......"+this.getClass());

		if(!file.isEmpty())
		{
			try {				
				byte[] bytes=file.getBytes();
				
				//check Excel Format
				
				format=excel.checkExcelFormatPpMastersMapping(file);
				
				if(format==true)
				{
					//check file cmpid is same as session cmp id

					cmpid=excel.checkCmp(file, sesscmpid);

					if(cmpid==true)
					{
						// Creating the directory to store file
						String rootPath = System.getProperty("catalina.home");
						File dir = new File(rootPath + File.separator + "tmpFiles");
						if (!dir.exists())
							dir.mkdirs();

						// Create the file on server
						File serverFile = new File(dir.getAbsolutePath()
								+ File.separator + file.getOriginalFilename());
						BufferedOutputStream stream = new BufferedOutputStream(
								new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();

						logger.info("Server File Location="
								+ serverFile.getAbsolutePath(), this.getClass());

						jsonresponse=excel.excelReadDatamapping(file.getOriginalFilename());

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

	@PostMapping(value="/getppmasteridnames")
	public List<PpMasterDTO> getPpMasterIdNames(@RequestBody PpMasterDTO ppmasterdto)
	{
		List<PpMasterDTO> ppmasternames = null;
		try {
			ppmasternames=tallymappingservice.getPpMasterIdNames(ppmasterdto.getMastertype(), ppmasterdto.getCmpid(), ppmasterdto.getPpmastername());
			logger.debug("Request......getPpMasterIdNames List......", this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return ppmasternames;
	}

	@PostMapping(value="/gettallymasteridnames")
	public List<TallyMastersDTO> getTallyMasterIdNames(@RequestBody TallyMastersDTO tallymasterdto)
	{
		List<TallyMastersDTO> tallymasters = null;
		try {
			tallymasters=tallymappingservice.getTallyMasterIdNames(tallymasterdto.getMasterType(), tallymasterdto.getCmpId(), tallymasterdto.getTallyMasterName());
			logger.debug("Request......getTallyMasterIdNames List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return tallymasters;
	}
}
