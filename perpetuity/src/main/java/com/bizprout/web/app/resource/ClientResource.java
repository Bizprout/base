package com.bizprout.web.app.resource;

import java.util.ArrayList;
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

import com.bizprout.web.api.service.BaseService;
import com.bizprout.web.app.dto.ClientDTO;
import com.bizprout.web.app.service.ClientServiceImpl;

@RestController
@RequestMapping("/client")
public class ClientResource {

	@Autowired
	private BaseService<ClientDTO> baseService;
	@Autowired
	private ClientServiceImpl clientService;

	Logger logger=LoggerFactory.getLogger(this.getClass());    

	public ClientResource()
	{
		System.out.println(this.getClass().getSimpleName() + "Created...");
		logger.debug(this.getClass().getSimpleName() + "Created...");
	}

	@PostMapping(value="/add", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> addClient(@RequestBody ClientDTO clientdto)    
	{
		baseService.testService(clientdto);
		logger.info("Request.......adduser method......");

		if(clientdto.getClientId()>0)
		{
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("failure", HttpStatus.OK);
		}		
	}

	@PostMapping(value="/edit", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> editClient(@RequestBody ClientDTO clientdto)
	{
		int result=clientService.updateservice(clientdto);
		logger.info("Request.......Edit client method......");

		if(result>0)
		{
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("failure", HttpStatus.OK);
		}
	}

	@GetMapping(value="/getclientnames")
	@ResponseBody
	public List<ClientDTO> getClientNames()
	{
		List<ClientDTO> cdto = null;
		try {
			cdto=clientService.getClientNames();
			logger.debug("Request......getClient Name List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cdto;
	}
	
	@GetMapping(value="/getclientidname")
	@ResponseBody
	public List<ClientDTO> getClientIdName()
	{
		List<ClientDTO> cdto = null;
		try {
			cdto=clientService.getClientIdName();
			logger.debug("Request......getClientIdName List......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cdto;
	}

	@PostMapping(value="/getclientdata")
	public ResponseEntity<ClientDTO> getClientData(@RequestBody ClientDTO clientdto)
	{
		ClientDTO cDTO=clientService.getClientData(clientdto.getClientId());
		logger.info("Request.......getclientdata method......");

		return new ResponseEntity<ClientDTO>(cDTO, HttpStatus.OK);
	}

	@GetMapping(value="/getalldetails")
	@ResponseBody
	public  List<ClientDTO> getClient()
	{
		logger.info("Request.......get user method......");
		List<ClientDTO> listOfClients= new ArrayList<ClientDTO>();        
		listOfClients = clientService.getService();
		return listOfClients;		
	}
}
