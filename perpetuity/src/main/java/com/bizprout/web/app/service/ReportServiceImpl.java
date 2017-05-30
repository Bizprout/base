package com.bizprout.web.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizprout.web.api.service.ReportService;
import com.bizprout.web.app.dto.ReportsDTO;
import com.bizprout.web.app.repository.ReportRepositoryImpl;

@Service
@Transactional
public class ReportServiceImpl implements ReportService<ReportsDTO>
{
	@Autowired
	private ReportRepositoryImpl repRepository;

	public ReportServiceImpl() {}

	Logger logger = LoggerFactory.getLogger(getClass());

	public List<Object> getCmpTrialBal(ReportsDTO repdto) {
		List<Object> cmpTB = null;
		try
		{
			logger.info("inside getCmpTrialBal ");

			cmpTB = repRepository.getCompanyTB(repdto);

		}
		catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return cmpTB;
	}

	public List<Object> getCmpDaybook(ReportsDTO repdto) {
		List<Object> vchData = null;
		try
		{
			logger.info("inside getCmpDaybook");

			vchData = repRepository.getCmpDaybook(repdto);

		}
		catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return vchData;
	}


	public List<Object> getVchLedgers(String voucherId) {
		List<Object> vchData = null;
		try
		{
			logger.info("inside getVchLedgers ");

			vchData = repRepository.getVchLedgers(voucherId);

		}
		catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return vchData;
	}

	@Override
	public List<Object> getCmpVchData(ReportsDTO repdto) {
		List<Object> vchData = null;
		try
		{
			logger.info("inside getCmpVchData ");
			vchData = repRepository.getCmpVchData(repdto);

		}
		catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return vchData;


	}

}