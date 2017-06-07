package com.bizprout.web.app.resource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("deprecation")
public class ExcelReadData {


	public boolean checkExcelFormatPpMasters(MultipartFile filename)
	{
		Logger logger=LoggerFactory.getLogger(this.getClass());

		Workbook workbook = null;

		boolean res = false;

		try {

			InputStream excelfile= filename.getInputStream();
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);


			if(datatypesheet.getRow(0).getCell(0).getStringCellValue().equals("Upload Format for Creating PP Masters") &&
					datatypesheet.getRow(3).getCell(0).getStringCellValue().equals("Master Type") && 
					datatypesheet.getRow(3).getCell(1).getStringCellValue().equals("PP Master Name") &&
					datatypesheet.getRow(3).getCell(2).getStringCellValue().equals("Category") &&
					datatypesheet.getRow(3).getCell(3).getStringCellValue().equals("PP Parent Name") &&
					datatypesheet.getRow(1).getCell(0).getStringCellValue().contains("Company Name:"))
			{
				res=true;
			}
			else
			{
				res=false;
			}

		}
		catch (IOException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
			res=false;
		}
		finally{
			try {
				workbook.close();
			} catch (IOException e) {
				logger.error(e.getMessage()+"...."+this.getClass());
			}
		}
		return res;

	}

	public boolean checkExcelFormatPpMastersMapping(MultipartFile filename)
	{
		Logger logger=LoggerFactory.getLogger(this.getClass());

		Workbook workbook = null;

		boolean res = false;

		try {

			InputStream excelfile= filename.getInputStream();
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);


			if(datatypesheet.getRow(0).getCell(0).getStringCellValue().equals("Upload Format for Mapping PP Masters") &&
					datatypesheet.getRow(1).getCell(0).getStringCellValue().contains("Client Name:") &&
					datatypesheet.getRow(2).getCell(0).getStringCellValue().contains("Company Name:") &&
					datatypesheet.getRow(3).getCell(0).getStringCellValue().equals("Master Type") && 
					datatypesheet.getRow(3).getCell(1).getStringCellValue().equals("Map to PP Master") &&
					datatypesheet.getRow(3).getCell(2).getStringCellValue().equals("Tally Master Names"))
			{
				res=true;
			}
			else
			{
				res=false;
			}

		}
		catch (IOException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
			res=false;
		}
		finally{
			try {
				workbook.close();
			} catch (IOException e) {
				logger.error(e.getMessage()+"...."+this.getClass());
			}
		}
		return res;

	}

	public boolean checkCmp(MultipartFile filename, int sesscmpid){

		Logger logger=LoggerFactory.getLogger(this.getClass());

		Workbook workbook = null;

		String[] compname = null;

		String linecmp="";

		String compdata=null;

		int cmpId=0;

		boolean res = false;

		try {
			
			Properties prop = new Properties();
			InputStream inprop = null;
			
			inprop = new FileInputStream("Postgeturls.properties");

			// load a properties file
			prop.load(inprop);
			
			String baseserveraddress=prop.getProperty("baseserveraddress");
			String getcompanyidbyname=prop.getProperty("getcompanyidbyname");

			InputStream excelfile= filename.getInputStream();
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);

			if(datatypesheet.getRow(1).getCell(0).getStringCellValue().contains("Company Name: "))
			{
				compname=datatypesheet.getRow(1).getCell(0).getStringCellValue().split("Company Name: ");
			}
			else
			{
				compname=datatypesheet.getRow(2).getCell(0).getStringCellValue().split("Company Name: ");
			}		

			//request to post cmpname and get cmp_id
			DefaultHttpClient httpclientcmp= new DefaultHttpClient();
			HttpPost postcmp= new HttpPost(baseserveraddress+getcompanyidbyname);
			StringEntity inputcmp= new StringEntity("{\"tallyCmpName\":\"" + compname[1] + "\"}");					
			inputcmp.setContentType("application/json");
			postcmp.setEntity(inputcmp);
			logger.debug("Posting excel data to get cmpid....."+this.getClass());
			HttpResponse responsecmp=httpclientcmp.execute(postcmp);			

			BufferedReader rdcmp= new BufferedReader(new InputStreamReader(responsecmp.getEntity().getContent()));
			if((linecmp=rdcmp.readLine())!=null)
			{
				logger.debug("get company data response..."+linecmp+"....class name..."+this.getClass());
				compdata=linecmp;
			}

			if(compdata!=null)
			{
				JSONObject object = new JSONObject(compdata);
				if(object.get("tallyCmpName").equals(compname[1]))
				{
					cmpId=object.getInt("cmpId");
				}
				httpclientcmp.close();	

				if(cmpId==sesscmpid)
				{
					res= true;
				}
				else
				{
					res=false;
				}
			}
			else
			{
				res=false;
			}

		}
		catch (IOException | JSONException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
			res=false;
		}
		finally{
			try {
				workbook.close();
			} catch (IOException e) {
				logger.error(e.getMessage()+"...."+this.getClass());
			}
		}
		return res;
	}


	@SuppressWarnings("resource")
	public List<Object> excelReadData(MultipartFile file){

		Logger logger=LoggerFactory.getLogger(this.getClass());

		Workbook workbook;

		String line="";
		String line1="";
		String linecmp="";
		String linecstcat="";
		String linegrps1="";
		String linecstcent="";
		String[] compname = null;
		String[] categories={"Assets","Liabilities","Expenses","Income"};
		String compdata=null;
		int cmpId=0;
		List<Object> result=new ArrayList<Object>();

		logger.debug("inside Excel read data class..."+this.getClass());
		try {
			
			Properties prop = new Properties();
			InputStream inprop = null;
			
			inprop = new FileInputStream("Postgeturls.properties");

			// load a properties file
			prop.load(inprop);
			
			String baseserveraddress=prop.getProperty("baseserveraddress");
			String getcompanyidbyname=prop.getProperty("getcompanyidbyname");
			String getppmastersnameall=prop.getProperty("getppmastersnameall");
			String getppmastersnamebycompany=prop.getProperty("getppmastersnamebycompany");
			String ppmasteradd=prop.getProperty("ppmasteradd");

			InputStream excelfile= file.getInputStream();
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);
			Iterator<Row> iterator=datatypesheet.iterator();

			JSONArray jsonarr=new JSONArray();

			compname=datatypesheet.getRow(1).getCell(0).getStringCellValue().split("Company Name: ");

			//request to post cmpname and get cmp_id
			DefaultHttpClient httpclientcmp= new DefaultHttpClient();
			HttpPost postcmp= new HttpPost(baseserveraddress+getcompanyidbyname);
			StringEntity inputcmp= new StringEntity("{\"tallyCmpName\":\"" + compname[1] + "\"}");					
			inputcmp.setContentType("application/json");
			postcmp.setEntity(inputcmp);
			logger.debug("Posting excel data to get cmpid....."+this.getClass());
			HttpResponse responsecmp=httpclientcmp.execute(postcmp);			

			BufferedReader rdcmp= new BufferedReader(new InputStreamReader(responsecmp.getEntity().getContent()));
			if((linecmp=rdcmp.readLine())!=null)
			{
				logger.debug("get company data response..."+linecmp+"....class name..."+this.getClass());
				compdata=linecmp;
			}						
			JSONObject object = new JSONObject(compdata);
			if(object.get("tallyCmpName").equals(compname[1]))
			{
				cmpId=object.getInt("cmpId");
			}

			if(cmpId==0)
			{
				result.add("Company Does not match!");
			}

			//request to post mastertype, category and cmpid and get all cost categories
			DefaultHttpClient httpclientcstcat= new DefaultHttpClient();
			HttpPost postcstcat= new HttpPost(baseserveraddress+getppmastersnameall);
			StringEntity inputcstcat= new StringEntity("{\"mastertype\":\"Cost Category\",\"category\":\"Cost Category\",\"cmpid\":\""+cmpId+"\"}");					
			inputcstcat.setContentType("application/json");
			postcstcat.setEntity(inputcstcat);
			logger.debug("Posting excel data to get all cost categories....."+this.getClass());
			HttpResponse responsecstcat=httpclientcstcat.execute(postcstcat);

			BufferedReader rdcstcat= new BufferedReader(new InputStreamReader(responsecstcat.getEntity().getContent()));
			if((linecstcat=rdcstcat.readLine())!=null)
			{
				logger.debug("get cost categories data response..."+linecstcat+"....class name..."+this.getClass());
			}

			JSONArray cstcat=new JSONArray(linecstcat);

			//request to post mastertype and cmpid and get all cost centres
			DefaultHttpClient httpclientcstcent= new DefaultHttpClient();
			HttpPost postcstcent= new HttpPost(baseserveraddress+getppmastersnamebycompany);
			StringEntity inputcstcent= new StringEntity("{\"mastertype\":\"Cost Centre\",\"cmpid\":\""+cmpId+"\"}");					
			inputcstcent.setContentType("application/json");
			postcstcent.setEntity(inputcstcent);
			logger.debug("Posting excel data to get all cost centres....."+this.getClass());
			HttpResponse responsecstcent=httpclientcstcent.execute(postcstcent);

			BufferedReader rdcstcent= new BufferedReader(new InputStreamReader(responsecstcent.getEntity().getContent()));
			if((linecstcent=rdcstcent.readLine())!=null)
			{
				logger.debug("get cost centres data response..."+linecstcent+"....class name..."+this.getClass());
			}

			JSONArray cstcent=new JSONArray(linecstcent);

			if(iterator.hasNext())
			{
				while (iterator.hasNext()) {
					Row currentrow=iterator.next();

					if(currentrow.getRowNum()>3)
					{						
						JSONObject jsonobj = new JSONObject();

						//getCellTypeEnum shown as deprecated for version 3.15
						//getCellTypeEnum ill be renamed to getCellType starting from version 4.0

						jsonobj.put("cmpid", cmpId);

						if(currentrow.getCell(0).getStringCellValue().equals("Ledger") ||
								currentrow.getCell(0).getStringCellValue().equals("Group") ||
								currentrow.getCell(0).getStringCellValue().equals("Cost Category") ||
								currentrow.getCell(0).getStringCellValue().equals("Cost Centre") ||
								currentrow.getCell(0).getStringCellValue().equals("Voucher Type"))
						{
							jsonobj.put("mastertype", currentrow.getCell(0).getStringCellValue());
						}
						else
						{
							result.add("Master Type Does not match at row "+(currentrow.getRowNum()+1));
						}

						jsonobj.put("ppmastername", currentrow.getCell(1).getStringCellValue());

						if(currentrow.getCell(0).getStringCellValue().equals("Ledger") ||
								currentrow.getCell(0).getStringCellValue().equals("Group"))
						{
							if(Arrays.asList(categories).contains(currentrow.getCell(2).getStringCellValue()))
							{
								jsonobj.put("category", currentrow.getCell(2).getStringCellValue());

								if(currentrow.getCell(2).getStringCellValue().equals("Assets"))
								{
									//request to post mastertype, category and cmpid and get ppmaster names category specific
									DefaultHttpClient httpclientgrps1= new DefaultHttpClient();
									HttpPost postgrps1= new HttpPost(baseserveraddress+getppmastersnameall);
									StringEntity inputgrps1= new StringEntity("{\"mastertype\":\"Group\",\"category\":\""+currentrow.getCell(2).getStringCellValue()+"\",\"cmpid\":\""+cmpId+"\"}");					
									inputgrps1.setContentType("application/json");
									postgrps1.setEntity(inputgrps1);
									logger.debug("Posting excel data to get all groups....."+this.getClass());
									HttpResponse responsegrps1=httpclientgrps1.execute(postgrps1);

									BufferedReader rdgrps1= new BufferedReader(new InputStreamReader(responsegrps1.getEntity().getContent()));
									if((linegrps1=rdgrps1.readLine())!=null)
									{
										logger.debug("get company data response..."+linegrps1+"....class name..."+this.getClass());
									}

									JSONArray catspecppmasters=new JSONArray(linegrps1);

									if(currentrow.getCell(3).getStringCellValue().equals("Assets") || catspecppmasters.toString().contains(currentrow.getCell(3).getStringCellValue()))
									{
										jsonobj.put("ppparentname", currentrow.getCell(3).getStringCellValue());
									}
									else
									{
										result.add("Pp Parent Name Does not match at row "+(currentrow.getRowNum()+1));
									}
								}
								else if(currentrow.getCell(2).getStringCellValue().equals("Liabilities"))
								{
									//request to post mastertype, category and cmpid and get ppmaster names category specific
									DefaultHttpClient httpclientgrps1= new DefaultHttpClient();
									HttpPost postgrps1= new HttpPost(baseserveraddress+getppmastersnameall);
									StringEntity inputgrps1= new StringEntity("{\"mastertype\":\"Group\",\"category\":\""+currentrow.getCell(2).getStringCellValue()+"\",\"cmpid\":\""+cmpId+"\"}");					
									inputgrps1.setContentType("application/json");
									postgrps1.setEntity(inputgrps1);
									logger.debug("Posting excel data to get all groups....."+this.getClass());
									HttpResponse responsegrps1=httpclientgrps1.execute(postgrps1);

									BufferedReader rdgrps1= new BufferedReader(new InputStreamReader(responsegrps1.getEntity().getContent()));
									if((linegrps1=rdgrps1.readLine())!=null)
									{
										logger.debug("get company data response..."+linegrps1+"....class name..."+this.getClass());

									}
									JSONArray catspecppmasters=new JSONArray(linegrps1);

									if(currentrow.getCell(3).getStringCellValue().equals("Liabilities") || catspecppmasters.toString().contains(currentrow.getCell(3).getStringCellValue()))
									{
										jsonobj.put("ppparentname", currentrow.getCell(3).getStringCellValue());
									}
									else
									{
										result.add("Pp Parent Name Does not match at row "+(currentrow.getRowNum()+1));
									}
								}
								else if(currentrow.getCell(2).getStringCellValue().equals("Expenses"))
								{
									//request to post mastertype, category and cmpid and get ppmaster names category specific
									DefaultHttpClient httpclientgrps1= new DefaultHttpClient();
									HttpPost postgrps1= new HttpPost(baseserveraddress+getppmastersnameall);
									StringEntity inputgrps1= new StringEntity("{\"mastertype\":\"Group\",\"category\":\""+currentrow.getCell(2).getStringCellValue()+"\",\"cmpid\":\""+cmpId+"\"}");					
									inputgrps1.setContentType("application/json");
									postgrps1.setEntity(inputgrps1);
									logger.debug("Posting excel data to get all groups....."+this.getClass());
									HttpResponse responsegrps1=httpclientgrps1.execute(postgrps1);

									BufferedReader rdgrps1= new BufferedReader(new InputStreamReader(responsegrps1.getEntity().getContent()));
									if((linegrps1=rdgrps1.readLine())!=null)
									{
										logger.debug("get company data response..."+linegrps1+"....class name..."+this.getClass());

									}

									JSONArray catspecppmasters=new JSONArray(linegrps1);

									if(currentrow.getCell(3).getStringCellValue().equals("Expenses") || catspecppmasters.toString().contains(currentrow.getCell(3).getStringCellValue()))
									{
										jsonobj.put("ppparentname", currentrow.getCell(3).getStringCellValue());
									}
									else
									{
										result.add("Pp Parent Name Does not match at row "+(currentrow.getRowNum()+1));
									}
								}
								else if(currentrow.getCell(2).getStringCellValue().equals("Income"))
								{
									//request to post mastertype, category and cmpid and get ppmaster names category specific
									DefaultHttpClient httpclientgrps1= new DefaultHttpClient();
									HttpPost postgrps1= new HttpPost(baseserveraddress+getppmastersnameall);
									StringEntity inputgrps1= new StringEntity("{\"mastertype\":\"Group\",\"category\":\""+currentrow.getCell(2).getStringCellValue()+"\",\"cmpid\":\""+cmpId+"\"}");					
									inputgrps1.setContentType("application/json");
									postgrps1.setEntity(inputgrps1);
									logger.debug("Posting excel data to get all groups....."+this.getClass());
									HttpResponse responsegrps1=httpclientgrps1.execute(postgrps1);

									BufferedReader rdgrps1= new BufferedReader(new InputStreamReader(responsegrps1.getEntity().getContent()));
									if((linegrps1=rdgrps1.readLine())!=null)
									{
										logger.debug("get company data response..."+linegrps1+"....class name..."+this.getClass());

									}

									JSONArray catspecppmasters=new JSONArray(linegrps1);

									if(currentrow.getCell(3).getStringCellValue().equals("Income") || catspecppmasters.toString().contains(currentrow.getCell(3).getStringCellValue()))
									{
										jsonobj.put("ppparentname", currentrow.getCell(3).getStringCellValue());
									}
									else
									{
										result.add("Pp Parent Name Does not match at row "+(currentrow.getRowNum()+1));
									}
								}								
							}
							else
							{
								result.add("Category Does not match at row "+(currentrow.getRowNum()+1));
							}							
						}
						else if(currentrow.getCell(0).getStringCellValue().equals("Cost Category"))
						{
							if(currentrow.getCell(2).getStringCellValue().equals("Cost Category"))
							{
								jsonobj.put("category", currentrow.getCell(2).getStringCellValue());

								if(currentrow.getCell(3).getStringCellValue().equals("Primary") || cstcat.toString().contains(currentrow.getCell(3).getStringCellValue()))
								{
									jsonobj.put("ppparentname", currentrow.getCell(3).getStringCellValue());
								}
								else
								{
									result.add("Pp Parent Name Does not match at row "+(currentrow.getRowNum()+1));
								}
							}
							else
							{
								result.add("Category Does not match at row "+(currentrow.getRowNum()+1));
							}
						}
						else if(currentrow.getCell(0).getStringCellValue().equals("Cost Centre"))
						{
							if(currentrow.getCell(2).getStringCellValue().equals("Primary") ||
									cstcat.toString().contains(currentrow.getCell(2).getStringCellValue()))
							{
								jsonobj.put("category", currentrow.getCell(2).getStringCellValue());
							}
							else
							{
								result.add("Category Does not match at row "+(currentrow.getRowNum()+1));
							}

							if(currentrow.getCell(3).getStringCellValue().equals("Primary") || cstcent.toString().contains(currentrow.getCell(3).getStringCellValue()))
							{
								jsonobj.put("ppparentname", currentrow.getCell(3).getStringCellValue());
							}
							else
							{
								result.add("Pp Parent Name Does not match at row "+(currentrow.getRowNum()+1));
							}
						}
						else if(currentrow.getCell(0).getStringCellValue().equals("Voucher Type"))
						{
							if(currentrow.getCell(2).getStringCellValue().equals("Voucher Type"))
							{
								jsonobj.put("category", currentrow.getCell(2).getStringCellValue());
								
								if(currentrow.getCell(3).getStringCellValue().equals("Contra") || 
										currentrow.getCell(3).getStringCellValue().equals("Credit Note") || 
										currentrow.getCell(3).getStringCellValue().equals("Debit Note") || 
										currentrow.getCell(3).getStringCellValue().equals("Job Work In Order") ||
										currentrow.getCell(3).getStringCellValue().equals("Job Work Out Order") ||
										currentrow.getCell(3).getStringCellValue().equals("Journal") ||
										currentrow.getCell(3).getStringCellValue().equals("Material In") ||
										currentrow.getCell(3).getStringCellValue().equals("Material Out") ||
										currentrow.getCell(3).getStringCellValue().equals("Memorandum") ||
										currentrow.getCell(3).getStringCellValue().equals("Payment") ||
										currentrow.getCell(3).getStringCellValue().equals("Physical Stock") ||
										currentrow.getCell(3).getStringCellValue().equals("Purchase") ||
										currentrow.getCell(3).getStringCellValue().equals("Purchase Order") ||
										currentrow.getCell(3).getStringCellValue().equals("Receipt") ||
										currentrow.getCell(3).getStringCellValue().equals("Receipt Note") ||
										currentrow.getCell(3).getStringCellValue().equals("Rejections In") ||
										currentrow.getCell(3).getStringCellValue().equals("Rejections Out") ||
										currentrow.getCell(3).getStringCellValue().equals("Reversing Journal") ||
										currentrow.getCell(3).getStringCellValue().equals("Sales") ||
										currentrow.getCell(3).getStringCellValue().equals("Sales Order") ||
										currentrow.getCell(3).getStringCellValue().equals("Stock Journal"))
								{
									jsonobj.put("ppparentname", currentrow.getCell(3).getStringCellValue());
								}
								else
								{
									result.add("Pp Parent Name Does not match at row "+(currentrow.getRowNum()+1));
								}
							}
							else
							{
								result.add("Category does not Match at row "+(currentrow.getRowNum()+1));
							}
						}

						jsonarr.put(jsonobj);		
					}
				}
			}	

			// Create the JSON.

			logger.debug("Json String...."+jsonarr.toString()+"...class name...."+this.getClass());

			workbook.close();

			//Call the service and import the data
			
			if(jsonarr.length()>0)
			{
				for (int i = 0; i < jsonarr.length(); i++) {

					DefaultHttpClient httpclient= new DefaultHttpClient();
					HttpPost post= new HttpPost(baseserveraddress+ppmasteradd);
					StringEntity input= new StringEntity(jsonarr.getString(i));
					input.setContentType("application/json");
					post.setEntity(input);
					logger.debug("Posting excel data to ppmaster add service....."+this.getClass());
					HttpResponse response=httpclient.execute(post);

					BufferedReader rd= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					while((line=rd.readLine())!=null)
					{
						logger.debug("post data response..."+line+"....class name..."+this.getClass());
						line1=line;
					}
				}
				
				if(result.isEmpty())
				{
					if(line1.contains("success"))
					{
						result.add("success");
					}			
					else
					{
						result.add("failure");
					}
				}
			}
			else
			{
				result.add("No Data Filled.");
			}

		} catch (IOException | JSONException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}

		return result;
	}

	@SuppressWarnings("resource")
	public List<Object> excelReadDatamapping(MultipartFile file){

		Logger logger=LoggerFactory.getLogger(this.getClass());

		Workbook workbook;

		String line=null;
		String line1=null;
		String line2=null;
		String line3=null;
		String line4=null;
		String line5=null;
		String line6=null;
		String compdata=null;
		String[] compname = null;
		int cmpId=0;
		JSONArray ppid = null;
		JSONArray masterid = null;
		int ppmasterid=0;
		String cate=null;
		int tallymasterid=0;
		List<Object> result=new ArrayList<Object>();

		logger.debug("inside Excel ReadDatamapping class..."+this.getClass());
		try {
			
			Properties prop = new Properties();
			InputStream inprop = null;
			
			inprop = new FileInputStream("Postgeturls.properties");

			// load a properties file
			prop.load(inprop);
			
			String baseserveraddress=prop.getProperty("baseserveraddress");
			String getcompanyidbyname=prop.getProperty("getcompanyidbyname");
			String getppmasternames=prop.getProperty("getppmasternames");
			String getppmasteridnames=prop.getProperty("getppmasteridnames");
			String gettallymasternames=prop.getProperty("gettallymasternames");
			String gettallymasteridnames=prop.getProperty("gettallymasteridnames");
			String saveppmapping=prop.getProperty("saveppmapping");

			InputStream excelfile= file.getInputStream();
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);
			Iterator<Row> iterator=datatypesheet.iterator();

			JSONArray jsonarr=new JSONArray();

			compname=datatypesheet.getRow(2).getCell(0).getStringCellValue().split("Company Name: ");

			//request to post cmpname and get cmp_id
			DefaultHttpClient httpclientcmp= new DefaultHttpClient();
			HttpPost postcmp= new HttpPost(baseserveraddress+getcompanyidbyname);
			StringEntity inputcmp= new StringEntity("{\"tallyCmpName\":\"" + compname[1] + "\"}");					
			inputcmp.setContentType("application/json");
			postcmp.setEntity(inputcmp);
			logger.debug("Posting excel data to get cmpid....."+this.getClass());
			HttpResponse responsecmp=httpclientcmp.execute(postcmp);			

			BufferedReader rdcmp= new BufferedReader(new InputStreamReader(responsecmp.getEntity().getContent()));
			if((line=rdcmp.readLine())!=null)
			{
				logger.debug("get company data response..."+line+"....class name..."+this.getClass());
				compdata=line;
			}						
			JSONObject object = new JSONObject(compdata);
			if(object.get("tallyCmpName").equals(compname[1]))
			{
				cmpId=object.getInt("cmpId");
			}

			if(cmpId==0)
			{
				result.add("Company Does not match!");
			}

			if(iterator.hasNext())
			{
				while (iterator.hasNext()) {
					Row currentrow=iterator.next();

					if(currentrow.getRowNum()>3)
					{						
						JSONObject jsonobj = new JSONObject();

						jsonobj.put("cmpId", cmpId);

						if(currentrow.getCell(0).getStringCellValue().equals("Ledger") ||
								currentrow.getCell(0).getStringCellValue().equals("Group") ||
								currentrow.getCell(0).getStringCellValue().equals("Cost Category") ||
								currentrow.getCell(0).getStringCellValue().equals("Cost Centre") ||
								currentrow.getCell(0).getStringCellValue().equals("Voucher Type"))
						{

						}
						else
						{
							result.add("Master Type Does not match at row "+(currentrow.getRowNum()+1));
						}

						//get all the pp master for the master type and cmpid

						//request to post mastertype and cmpid and get ppmaster names
						DefaultHttpClient httpclientgrps1= new DefaultHttpClient();
						HttpPost postgrps1= new HttpPost(baseserveraddress+getppmasternames);
						StringEntity inputgrps1= new StringEntity("{\"mastertype\":\""+currentrow.getCell(0).getStringCellValue()+"\",\"cmpid\":\""+cmpId+"\"}");					
						inputgrps1.setContentType("application/json");
						postgrps1.setEntity(inputgrps1);
						logger.debug("Posting excel data to get all pp master names....."+this.getClass());
						HttpResponse responsegrps1=httpclientgrps1.execute(postgrps1);

						BufferedReader rdgrps1= new BufferedReader(new InputStreamReader(responsegrps1.getEntity().getContent()));
						if((line1=rdgrps1.readLine())!=null)
						{
							logger.debug("get pp masters data response..."+line1+"....class name..."+this.getClass());
						}

						JSONArray ppmasternames=new JSONArray(line1);

						if(ppmasternames.toString().contains(currentrow.getCell(1).getStringCellValue()))
						{
							//request to get pp_id
							DefaultHttpClient httpclient1= new DefaultHttpClient();
							HttpPost post1= new HttpPost(baseserveraddress+getppmasteridnames);
							StringEntity input1= new StringEntity("{\"cmpid\":\"" + cmpId + "\",\"mastertype\":\"" + currentrow.getCell(0).getStringCellValue() + "\",\"ppmastername\":\"" + currentrow.getCell(1).getStringCellValue() + "\"}");					
							input1.setContentType("application/json");
							post1.setEntity(input1);
							logger.debug("Posting excel data to get ppid....."+this.getClass());
							HttpResponse response1=httpclient1.execute(post1);

							BufferedReader rd1= new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
							if((line2=rd1.readLine())!=null)
							{
								logger.debug("post data ppid response..."+line2+"....class name..."+this.getClass());
								ppid=new JSONArray(line2);

								for (int i = 0; i < ppid.length(); ++i) {
									JSONObject rec = ppid.getJSONObject(i);
									ppmasterid = rec.getInt("masteridindex");
									cate = rec.getString("category");
								}
							}
							
							jsonobj.put("ppid", ppmasterid);
						}
						else
						{
							result.add("PP Masters Name Does not match at row "+(currentrow.getRowNum()+1));
						}
						
						//request to get all the tally master names for the master type
						DefaultHttpClient httpclienttallymaster= new DefaultHttpClient();
						HttpPost posttallymaster= new HttpPost(baseserveraddress+gettallymasternames);
						StringEntity inputtallymaster= new StringEntity("{\"masterType\":\""+currentrow.getCell(0).getStringCellValue()+"\",\"cmpId\":\""+cmpId+"\", \"category\":\""+cate+"\"}");					
						inputtallymaster.setContentType("application/json");
						posttallymaster.setEntity(inputtallymaster);
						logger.debug("Posting excel data to get all cost centres....."+this.getClass());
						HttpResponse responsetallymaster=httpclienttallymaster.execute(posttallymaster);

						BufferedReader rdcstcent= new BufferedReader(new InputStreamReader(responsetallymaster.getEntity().getContent()));
						if((line3=rdcstcent.readLine())!=null)
						{
							logger.debug("get tally master names data response..."+line3+"....class name..."+this.getClass());
						}

						JSONArray tallymasternames=new JSONArray(line3);

						if(tallymasternames.toString().contains(currentrow.getCell(2).getStringCellValue()))
						{
							//request to get tally masters id
							DefaultHttpClient httpclient2= new DefaultHttpClient();
							HttpPost post2= new HttpPost(baseserveraddress+gettallymasteridnames);
							StringEntity input2= new StringEntity("{\"cmpId\":\"" + cmpId + "\",\"masterType\":\"" + currentrow.getCell(0).getStringCellValue() + "\",\"tallyMasterName\":\"" + currentrow.getCell(2).getStringCellValue() + "\"}");					
							input2.setContentType("application/json");
							post2.setEntity(input2);
							logger.debug("Posting excel data to get ppid....."+this.getClass());
							HttpResponse response2=httpclient2.execute(post2);

							BufferedReader rd2= new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
							if((line4=rd2.readLine())!=null)
							{
								logger.debug("post data ppid response..."+line4+"....class name..."+this.getClass());
								masterid=new JSONArray(line4);

								for (int i = 0; i < masterid.length(); ++i) {
									JSONObject rec1 = masterid.getJSONObject(i);
									tallymasterid = rec1.getInt("masterId");
								}
							}
							jsonobj.put("masterId", tallymasterid);	
						}
						else
						{
							result.add("Tally Masters Name Does not match at row "+(currentrow.getRowNum()+1));
						}
						jsonarr.put(jsonobj);		
					}
				}
			}

			// Create the JSON.

			logger.debug("Json String...."+jsonarr.toString()+"...class name...."+this.getClass());

			workbook.close();

			//Call the service and import the data

			if(jsonarr.length()>0)
			{
				DefaultHttpClient httpclient3= new DefaultHttpClient();
				HttpPost post3= new HttpPost(baseserveraddress+saveppmapping);
				StringEntity input3= new StringEntity(jsonarr.toString());
				input3.setContentType("application/json");
				post3.setEntity(input3);
				logger.debug("Posting excel data to ppmaster add service....."+this.getClass());
				HttpResponse response3=httpclient3.execute(post3);

				BufferedReader rd3= new BufferedReader(new InputStreamReader(response3.getEntity().getContent()));
				while((line5=rd3.readLine())!=null)
				{
					logger.debug("post data response..."+line5+"....class name..."+this.getClass());
					line6=line5;
				}

				if(result.isEmpty())
				{
					if(line6.contains("success"))
					{
						result.add("success");
					}			
					else
					{
						result.add("failure");
					}
				}
			}
			else
			{
				result.add("No Data Filled.");
			}


		} catch (IOException | JSONException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}

		return result;
	}
}
