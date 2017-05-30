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
		logger.debug(this.getClass().getSimpleName() + "Created...");
	}

	@PostMapping(value="/add", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addClient(@RequestBody @Valid ClientDTO clientdto, BindingResult result, Model model)    
	{
		List<Object> jsonresponse=new ArrayList<Object>();

		if(result.hasErrors())
		{
			jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList()));
			return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
		}

		try {
			clientService.testService(clientdto);
			logger.info("Request.......addClient method......"+this.getClass());

			if(clientdto.getClientId()>0)
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

	@PostMapping(value="/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editClient(@RequestBody @Valid ClientDTO clientdto, BindingResult result, Model model)
	{
		List<Object> jsonresponse=new ArrayList<Object>();

		if(result.hasErrors())
		{
			jsonresponse.add(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList()));
			return new ResponseEntity<Object>(jsonresponse, HttpStatus.OK);
		}

		try {
			int res=clientService.updateservice(clientdto);
			logger.info("Request.......Edit client method......"+this.getClass());


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

	@GetMapping(value="/getclientnames")
	@ResponseBody
	public List<ClientDTO> getClientNames()
	{
		List<ClientDTO> cdto = null;
		try {
			cdto=clientService.getClientNames();
			logger.debug("Request......getClient Name List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
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
			logger.debug("Request......getClientIdName List......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return cdto;
	}

	@PostMapping(value="/getclientdata")
	public ResponseEntity<ClientDTO> getClientData(@RequestBody ClientDTO clientdto)
	{
		ClientDTO cDTO=null;
		try {
			cDTO = clientService.getClientData(clientdto.getClientId());
			logger.info("Request.......getclientdata method......"+this.getClass());
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}

		return new ResponseEntity<ClientDTO>(cDTO, HttpStatus.OK);
	}

	@GetMapping(value="/getalldetails")
	@ResponseBody
	public  List<ClientDTO> getClient()
	{
		List<ClientDTO> listOfClients = null;
		try {
			logger.info("Request.......getClient method......"+this.getClass());
			listOfClients = new ArrayList<ClientDTO>();        
			listOfClients = clientService.getService();
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return listOfClients;		
	}
}
