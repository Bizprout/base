package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.ScreensDTO;
import com.bizprout.web.app.dto.UserMappingDTO;

public interface UserMappingService<T> {
	
	public List<String> getCompanyList();
	
	public List<ScreensDTO> getScreensList();
	
	public int updateUserMapping(UserMappingDTO t);
	
	public List<UserMappingDTO> getUserMapData(); 
	
	public UserMappingDTO getUserMapByCmpUser(int cmpid, int userid);
	
	public List<UserMappingDTO> getCmpByuserid(int userid);
	
	public int deleteScreensByCmpidUserid(int cmpid, int userid);
}
