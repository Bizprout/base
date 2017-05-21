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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bizprout.web.api.service.PpMasterService;
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.dto.UserEditVO;

@RestController
@RequestMapping("/ppmaster")
public class PpMasterResource {

	@Autowired
	private PpMasterService<PpMasterDTO> ppmasterservice;


	Logger logger=LoggerFactory.getLogger(this.getClass());

	public PpMasterResource() {
		try {
			System.out.println(this.getClass().getSimpleName() + "Created...");
			logger.debug(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			logger.debug("Request.......addppmaster method......");

			if(ppmasterDTO.getMasteridindex()>0)
			{
				jsonresponse.add("success");
			}
			else
			{
				jsonresponse.add("failure");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return new ResponseEntity<Object>(jsonresponse,HttpStatus.OK);
	}
	
	@PostMapping(value="/getppmastersnameall")
	public List<String> getPpMastersNameall(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersNameall(ppmasterDTO.getMastertype(), ppmasterDTO.getCategory(), ppmasterDTO.getCmpid());
			logger.debug("Request......getPpMastername List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmaster;
	}

	@PostMapping(value="/getppmastersname")
	public List<String> getPpMastersName(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersName(ppmasterDTO.getMastertype(), ppmasterDTO.getCategory(), ppmasterDTO.getCmpid(), ppmasterDTO.getPpmastername());
			logger.debug("Request......getPpMastername List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmaster;
	}
	
	@PostMapping(value="/getppmastersnamebycostcategory")
	public List<String> getPpMastersNameByCostCategory(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersNamebyCostCategory(ppmasterDTO.getMastertype(), ppmasterDTO.getCmpid(), ppmasterDTO.getPpmastername());
			logger.debug("Request......getPpMastername List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmaster;
	}
	
	@PostMapping(value="/getppmastersnamebycompany")
	public List<String> getPpMastersNameByCompany(@RequestBody PpMasterDTO ppmasterDTO){

		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersNameByCompany(ppmasterDTO.getMastertype(), ppmasterDTO.getCmpid());
			logger.debug("Request......getPpMastername List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmaster;
	}

	@PostMapping(value="/getppparentname")
	public List<PpMasterDTO> getPpParentName(@RequestBody PpMasterDTO ppmasterDTO){

		List<PpMasterDTO> ppparentname = null;
		try {
			ppparentname=ppmasterservice.getPpParentName(ppmasterDTO.getMastertype(), ppmasterDTO.getPpmastername(), ppmasterDTO.getCmpid());
			logger.debug("Request......getPpParentName List......");
		} catch (Exception e) {
			e.printStackTrace();
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
			logger.debug("Request.......EditPpMasters method......");

			if(res>0)
			{
				jsonresponse.add("success");
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

	@PostMapping(value="/getppmasterdata")
	@ResponseBody
	public List<PpMasterDTO> getPpMasterData(@RequestBody PpMasterDTO ppmasterDTO)
	{
		List<PpMasterDTO> ppmasterdata = null;
		try {
			ppmasterdata=ppmasterservice.getPpMasterdata(ppmasterDTO.getCmpid());
			logger.debug("Request......getPpMasterData......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasterdata;
	}
	
	@RequestMapping(value="/ppmasteruploadfile", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> pPmasterFileUpload(@RequestParam("file") MultipartFile file){
		
		String response;
		List<Object> jsonresponse=new ArrayList<Object>();

		if(!file.isEmpty())
		{
			try {				
				byte[] bytes=file.getBytes();
				
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
						+ serverFile.getAbsolutePath());
				
				ExcelReadData excel= new ExcelReadData();
				response=excel.excelReadData(file.getOriginalFilename());
								
				if(response.contains("success"))
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
		}
		else 
		{
			return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
	}
}

