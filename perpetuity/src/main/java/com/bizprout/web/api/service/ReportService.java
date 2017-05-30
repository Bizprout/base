package com.bizprout.web.api.service;

import java.util.List;

import com.bizprout.web.app.dto.ReportsDTO;

public abstract interface ReportService<T>
{
  public abstract List<Object> getCmpTrialBal(ReportsDTO t);
  
  public abstract List<Object> getCmpDaybook(ReportsDTO t);
  
  public abstract List<Object> getCmpVchData(ReportsDTO t);
  
  public abstract List<Object> getVchLedgers(String s);
  
  
  
}
