package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.BaseService;
import com.bizprout.web.app.dto.ClientDTO;
import com.bizprout.web.app.dto.UserDTO;
import com.bizprout.web.app.repository.ClientRepositoryImpl;

@Service
public class ClientServiceImpl implements BaseService<ClientDTO>{
	
	@Autowired
	private BaseRepository<ClientDTO> baseRepository;
	@Autowired
	private ClientRepositoryImpl clientRepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public void testService(ClientDTO clientdto) {
		System.out.println("inside test service " + clientdto);
		logger.info("inside test service " + clientdto);
		baseRepository.save(clientdto);		
	}
	
	public int updateservice(ClientDTO clientdto) {
		System.out.println("inside updateservice " + clientdto);
		logger.info("inside Update service " + clientdto);
		int res=clientRepo.updateClient(clientdto);		
		return res;
	}
	
	public List<ClientDTO> getClientNames() {
		List<ClientDTO> client = null;
		try {
			logger.info("inside getClientNames method ");
			client = clientRepo.getClientNames();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception in getClientNames method \t" + e.getMessage());
		}
		return client;
	}
	
	public List<ClientDTO> getClientIdName() {
		List<ClientDTO> client = null;
		try {
			logger.info("inside getClientIdName method ");
			client = clientRepo.getClientIdName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception in getClientIdName method \t" + e.getMessage());
		}
		return client;
	}
	
	public List<ClientDTO> getService() {		
		logger.info("inside get service " );
		logger.info("inside get service " + (clientRepo.getClients()));
		return clientRepo.getClients();		
	}
	
	public ClientDTO getClientData(int clientid){
		
		ClientDTO clientdata = null;
		try {
			logger.info("inside getClientData method...");
			clientdata = clientRepo.getClientData(clientid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientdata;
	}

	public ClientDTO auth(ClientDTO t) {		
		return null;
	}
	
}
