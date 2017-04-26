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

	public TallyMappingResource() {
		try {
			logger.info(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PostMapping(value="/getcompanyidname")
	public List<CompanyDTO> getCompanyIdName(@RequestBody CompanyDTO compdto)
	{
		List<CompanyDTO> cdto = null;
		try {
			cdto=tallymappingservice.getCompanyIdName(compdto.getClientId());
			logger.debug("Request......getCompanyIdName List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cdto;
	}

	@PostMapping(value="/gettallymasternames")
	public List<TallyMastersDTO> getTallyMasterNames(@RequestBody TallyMastersDTO tallymasterdto)
	{
		List<TallyMastersDTO> tallymasters = null;
		try {
			tallymasters=tallymappingservice.getTallyMasterNames(tallymasterdto.getMasterType(), tallymasterdto.getCmpId());
			logger.debug("Request......getTallyMasterNames List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tallymasters;
	}

	@PostMapping(value="/getppmasternames")
	public List<PpMasterDTO> getPpMasterNames(@RequestBody PpMasterDTO ppmasterdto)
	{
		List<PpMasterDTO> ppmasternames = null;
		try {
			ppmasternames=tallymappingservice.getPpMasterNames(ppmasterdto.getMastertype(), ppmasterdto.getCmpid());
			logger.debug("Request......getTallyMasterNames List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmasternames;
	}

	@PostMapping(value="/insert", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> insertTallyMapping(@RequestBody TallyMappingDTO tallymappingdto)
	{
		ResponseEntity<String> resp = null;
		try {
			logger.info("inside insertTallyMapping method ");
			tallymappingservice.insertTallyMapping(tallymappingdto);

			if(tallymappingdto.getMappingId()!=0)
			{
				resp= new ResponseEntity<String>("success", HttpStatus.OK);
			}
			else
			{
				resp= new ResponseEntity<String>("failure", HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception in insertTallyMapping method \t" + e.getMessage());
		}

		return resp;
	}
	
	@PostMapping(value="/update", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateTallyMapping(@RequestBody TallyMappingDTO tallymappingdto)
	{
		ResponseEntity<String> resp = null;
		int result=0;
		try {
			logger.info("inside updateTallyMapping method ");
			result=tallymappingservice.updateTallyMapping(tallymappingdto);

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
			logger.error("Exception in updateTallyMapping method \t" + e.getMessage());
		}

		return resp;
	}

	@PostMapping(value="/gettallymasterids")
	public List<Integer> getPpMastersMapping(@RequestBody TallyMappingDTO tallymappingdto)
	{
		List<Integer> ppmapping = null;
		try {
			ppmapping=tallymappingservice.getPpMastersMapping(tallymappingdto.getCmpId(), tallymappingdto.getPpId());
			logger.debug("Request......getTallyMasterids List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmapping;
	}
	
	@PostMapping(value="/gettallyppmappingdata")
	public List<TallyMappingDTO> getPpMasterData(@RequestBody TallyMappingDTO tallymappingdto)
	{
		List<TallyMappingDTO> tallyppmapdata = null;
		try {
			tallyppmapdata=tallymappingservice.getTallyPpMappingData(tallymappingdto.getCmpId());
			logger.debug("Request......getPpMasterData......");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tallyppmapdata;
	}
}
