package com.bizprout.web.app.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.ReportService;
import com.bizprout.web.app.dto.ReportsDTO;

@RestController
@RequestMapping({"/reports"})
public class ReportsResource
{
	@Autowired
	private ReportService<ReportsDTO> repService;
	Logger logger = LoggerFactory.getLogger(getClass());

	public ReportsResource() {
		try {
			logger.info(getClass().getSimpleName() + "Created...");
		}
		catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}
	}

	@PostMapping({"/getdata"})
	@ResponseBody 
	public List<Object> getRepData(@RequestBody ReportsDTO repdto)
	{
		String repName = repdto.getReportName();

		List<Object> data = null;
		try {
			logger.debug("Request......getCompany Trial Bal List......");
			if (repName.equalsIgnoreCase("Trial Balance")) {
				data = repService.getCmpTrialBal(repdto);

			}
			else
				data = repService.getCmpVchData(repdto);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return data;
	}

	@PostMapping({"/getledgerdata"})
	@ResponseBody 
	public List<Object> getVchLedgers(@RequestParam("vchdata") String voucherId)
	{
		List<Object> data = null;
		try {
			logger.debug("Request......getVchLedgers List......");
			data = repService.getVchLedgers(voucherId);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return data;
	}



}