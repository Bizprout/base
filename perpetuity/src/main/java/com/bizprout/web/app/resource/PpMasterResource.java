package com.bizprout.web.app.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.PpMasterService;
import com.bizprout.web.app.dto.EditPpMasterDTO;
import com.bizprout.web.app.dto.PpMasterDTO;
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
	@PostMapping(value="/add", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity addppmaster(@RequestBody PpMasterDTO ppmasterDTO)
	{
		ResponseEntity resp = null;
		try {
			ppmasterservice.CreatePpMaster(ppmasterDTO);
			logger.debug("Request.......addppmaster method......");
									
			if(ppmasterDTO.getMasteridindex()>0)
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
	
	@PostMapping(value="/getppmastersname")
	public List<String> getPpMastersName(@RequestBody PpMasterDTO ppmasterDTO){
				
		List<String> ppmaster = null;
		try {
			ppmaster=ppmasterservice.getPpMastersName(ppmasterDTO.getMastertype());
			logger.debug("Request......getPpMastername List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppmaster;
	}
	
	@PostMapping(value="/getppparentname")
	public List<String> getPpParentName(@RequestBody PpMasterDTO ppmasterDTO){
				
		List<String> ppparentname = null;
		try {
			ppparentname=ppmasterservice.getPpParentName(ppmasterDTO.getMastertype(), ppmasterDTO.getPpmastername());
			logger.debug("Request......getPpMastername List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ppparentname;
	}
	
	@PostMapping(value="/edit", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity EditPpMasters(@RequestBody EditPpMasterDTO editppmasterDTO)
	{
		ResponseEntity resp = null;
		try {
			int result=ppmasterservice.UpdatePpMasters(editppmasterDTO);
			logger.debug("Request.......EditPpMasters method......");
			
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
