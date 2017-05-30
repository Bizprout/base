package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.common.repository.BaseRepository;
import com.bizprout.web.api.service.ClientService;
import com.bizprout.web.app.dto.ClientDTO;
import com.bizprout.web.app.repository.ClientRepositoryImpl;

@Service
@Transactional
public class ClientServiceImpl implements ClientService<ClientDTO>{
	
	@Autowired
	private BaseRepository<ClientDTO> baseRepository;
	@Autowired
	private ClientRepositoryImpl clientRepo;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public void testService(ClientDTO clientdto) {
		try {
			logger.info("inside test service " + clientdto, this.getClass());
			baseRepository.save(clientdto);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}		
	}
	
	public int updateservice(ClientDTO clientdto) {
		int res = 0;
		try {
			logger.info("inside Update service " + clientdto, this.getClass());
			res = clientRepo.updateClient(clientdto);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}		
		return res;
	}
	
	public List<ClientDTO> getClientNames() {
		List<ClientDTO> client = null;
		try {
			logger.info("inside getClientNames method ", this.getClass());
			client = clientRepo.getClientNames();
		} catch (Exception e) {
			logger.error("Exception in getClientNames method \t" + e.getMessage(), this.getClass());
		}
		return client;
	}
	
	public List<ClientDTO> getClientIdName() {
		List<ClientDTO> client = null;
		try {
			logger.info("inside getClientIdName method "+this.getClass());
			client = clientRepo.getClientIdName();
		} catch (Exception e) {
			logger.error("Exception in getClientIdName method \t" + e.getMessage(), this.getClass());
		}
		return client;
	}
	
	public List<ClientDTO> getService() {		
		try {
			logger.info("inside get service " + (clientRepo.getClients()), this.getClass());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), this.getClass());
		}		
		return clientRepo.getClients();
	}
	
	public ClientDTO getClientData(int clientid){
		
		ClientDTO clientdata = null;
		try {
			logger.info("inside getClientData method..."+this.getClass());
			clientdata = clientRepo.getClientData(clientid);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return clientdata;
	}

	public ClientDTO auth(ClientDTO t) {		
		return null;
	}
	
}
