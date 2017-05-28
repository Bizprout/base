package com.bizprout.web.app.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
			logger.info(getClass().getSimpleName() + "Created..."+this.getClass());
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
			logger.debug("Request......getCompany Trial Bal List......"+this.getClass());
			if (repName.equalsIgnoreCase("Trial Balance")) {
				data = repService.getCmpTrialBal(repdto);
				logger.debug(repName,this.getClass());
			}
			else if (repName.equalsIgnoreCase("Daybook")) {
				data = repService.getCmpVchData(repdto);
				logger.debug(repName,this.getClass());
			}
			else if (repName.equalsIgnoreCase("Payment Register")) {
				data = repService.getPymtVchData(repdto);
				logger.debug(repName,this.getClass());
			}
			else
				logger.debug(repName+" Report not found..."+this.getClass());

		}
		catch (Exception e)
		{
			logger.error(e.getMessage()+"..."+this.getClass());
		}
		return data;
	}
}