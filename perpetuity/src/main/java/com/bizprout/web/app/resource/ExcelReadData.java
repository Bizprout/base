package com.bizprout.web.app.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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

	public boolean searchJsonArray(JSONArray jsonArray, String itemtosearch){
		boolean found = false;
		try {
			for (int i = 0; i < jsonArray.length(); i++)
			{
				System.out.println(jsonArray.getString(i)+"....."+itemtosearch);
				if (jsonArray.getString(i).equals(itemtosearch))
				{
					found = true;
				}
				else
				{
					found = false;
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return found;
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

			InputStream excelfile= filename.getInputStream();
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);

			compname=datatypesheet.getRow(1).getCell(0).getStringCellValue().split("Company Name: ");

			//request to post cmpname and get cmp_id
			DefaultHttpClient httpclientcmp= new DefaultHttpClient();
			HttpPost postcmp= new HttpPost("http://localhost:9090/perpetuity/company/getcompanyidbyname");
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
	public List<Object> excelReadData(String filename){

		Logger logger=LoggerFactory.getLogger(this.getClass());

		final String FILE_NAME="C:/Apache Software Foundation/Tomcat 8.0/tmpFiles/"+filename;

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
		//ArrayList<String> cstcat=new ArrayList<String>();
		//ArrayList<String> groups=new ArrayList<String>();
		//ArrayList<String> cstcent=new ArrayList<String>();
		//ArrayList<String> catspecppmasters=new ArrayList<String>();
		int cmpId=0;
		List<Object> result=new ArrayList<Object>();

		logger.debug("inside Excel read data class..."+this.getClass());
		try {

			FileInputStream excelfile= new FileInputStream(new File(FILE_NAME));
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);
			Iterator<Row> iterator=datatypesheet.iterator();

			JSONArray jsonarr=new JSONArray();

			compname=datatypesheet.getRow(1).getCell(0).getStringCellValue().split("Company Name: ");

			//request to post cmpname and get cmp_id
			DefaultHttpClient httpclientcmp= new DefaultHttpClient();
			HttpPost postcmp= new HttpPost("http://localhost:9090/perpetuity/company/getcompanyidbyname");
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
			HttpPost postcstcat= new HttpPost("http://localhost:9090/perpetuity/ppmaster/getppmastersnameall");
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
			HttpPost postcstcent= new HttpPost("http://localhost:9090/perpetuity/ppmaster/getppmastersnamebycompany");
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
									HttpPost postgrps1= new HttpPost("http://localhost:9090/perpetuity/ppmaster/getppmastersnameall");
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

									if(currentrow.getCell(3).getStringCellValue().equals("Assets") ||  searchJsonArray(catspecppmasters, currentrow.getCell(3).getStringCellValue())==true )
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
									HttpPost postgrps1= new HttpPost("http://localhost:9090/perpetuity/ppmaster/getppmastersnameall");
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

									if(currentrow.getCell(3).getStringCellValue().equals("Liabilities") || searchJsonArray(catspecppmasters, currentrow.getCell(3).getStringCellValue())==true )
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
									HttpPost postgrps1= new HttpPost("http://localhost:9090/perpetuity/ppmaster/getppmastersnameall");
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

									if(currentrow.getCell(3).getStringCellValue().equals("Expenses") || searchJsonArray(catspecppmasters, currentrow.getCell(3).getStringCellValue())==true )
									{
										jsonobj.put("pp parentname", currentrow.getCell(3).getStringCellValue());
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
									HttpPost postgrps1= new HttpPost("http://localhost:9090/perpetuity/ppmaster/getppmastersnameall");
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

									if(currentrow.getCell(3).getStringCellValue().equals("Income") || searchJsonArray(catspecppmasters, currentrow.getCell(3).getStringCellValue())==true )
									{
										jsonobj.put("pp parentname", currentrow.getCell(3).getStringCellValue());
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

								if(currentrow.getCell(3).getStringCellValue().equals("Primary") || searchJsonArray(cstcat, currentrow.getCell(3).getStringCellValue())==true)
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
							System.out.println(cstcat);

							if(currentrow.getCell(2).getStringCellValue().equals("Primary") ||
									searchJsonArray(cstcat, currentrow.getCell(2).getStringCellValue())==true)
							{
								jsonobj.put("category", currentrow.getCell(2).getStringCellValue());
							}
							else
							{
								result.add("Category Does not match at row "+(currentrow.getRowNum()+1));
							}

							if(currentrow.getCell(3).getStringCellValue().equals("Primary") || searchJsonArray(cstcent, currentrow.getCell(3).getStringCellValue())==true)
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
							if(currentrow.getCell(2).getStringCellValue().equals(""))
							{
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
								result.add("Category is not Applicable for Voucher Types at row "+(currentrow.getRowNum()+1));
							}
						}

						jsonarr.put(jsonobj);		
					}				
				}
			}
			else
			{
				result.add("No Data Filled");
			}			

			// Create the JSON.

			logger.debug("Json String...."+jsonarr.toString()+"...class name...."+this.getClass());

			workbook.close();

			//Call the service and import the data


			for (int i = 0; i < jsonarr.length(); i++) {

				DefaultHttpClient httpclient= new DefaultHttpClient();
				HttpPost post= new HttpPost("http://localhost:9090/perpetuity/ppmaster/add");
				StringEntity input= new StringEntity(jsonarr.getString(i));
				input.setContentType("application/json");
				post.setEntity(input);
				logger.debug("Posting excel data to ppmaster add service....."+this.getClass());
				HttpResponse response=httpclient.execute(post);

				BufferedReader rd= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				while((line=rd.readLine())!=null)
				{
					logger.debug("post data response..."+line+"....class name..."+this.getClass());
					if(line.contains("[failure]") || line.contains("[success]"))
					{
						line1=line;
						result.add(line1);
					}
				}
			}
		} catch (IOException | JSONException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}

		return result;
	}

	public String excelReadDatamapping(String filename){

		Logger logger=LoggerFactory.getLogger(this.getClass());

		final String FILE_NAME="C:/Apache Software Foundation/Tomcat 8.0/tmpFiles/"+filename;

		Workbook workbook;

		String line=null;
		String line1=null;
		String line2=null;
		String line3=null;
		String line4=null;
		String compdata=null;
		String[] compname = null;
		int cmpId=0;
		String Mastertype=null;
		String ppmastername=null;
		String tallymastername=null;
		JSONArray ppid = null;
		JSONArray masterid = null;
		int ppmasterid=0;
		int tallymasterid=0;

		logger.debug("inside Excel ReadDatamapping class..."+this.getClass());
		try {

			FileInputStream excelfile= new FileInputStream(new File(FILE_NAME));
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);
			Iterator<Row> iterator=datatypesheet.iterator();

			JSONArray jsonarr=new JSONArray();

			compname=datatypesheet.getRow(2).getCell(0).getStringCellValue().split("Company Name: ");

			//request to get cmp_id
			DefaultHttpClient httpclient= new DefaultHttpClient();
			HttpGet request= new HttpGet("http://localhost:9090/perpetuity/company/getcompanyidname");

			logger.debug("Posting excel data to company resource....."+this.getClass());
			HttpResponse response=httpclient.execute(request);

			BufferedReader rd= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			if((line=rd.readLine())!=null)
			{
				logger.debug("get company data response..."+line+"....class name..."+this.getClass());
				compdata=line;
			}

			JSONArray jArray = new JSONArray(compdata);       
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject object = jArray.optJSONObject(i);
				if(object.get("tallyCmpName").equals(compname[1]))
				{
					cmpId=object.getInt("cmpId");
				}
			}

			while (iterator.hasNext()) {
				Row currentrow=iterator.next();

				if(currentrow.getRowNum()>3)
				{						
					JSONObject jsonobj = new JSONObject();

					jsonobj.put("cmpId", cmpId);

					Mastertype=currentrow.getCell(0).getStringCellValue();
					ppmastername=currentrow.getCell(1).getStringCellValue();
					tallymastername=currentrow.getCell(2).getStringCellValue();

					//request to get pp_id
					DefaultHttpClient httpclient1= new DefaultHttpClient();
					HttpPost post1= new HttpPost("http://localhost:9090/perpetuity/pptallymapping/getppmasteridnames");
					StringEntity input1= new StringEntity("{\"cmpid\":\"" + cmpId + "\",\"mastertype\":\"" + Mastertype + "\",\"ppmastername\":\"" + ppmastername + "\"}");					
					input1.setContentType("application/json");
					post1.setEntity(input1);
					logger.debug("Posting excel data to get ppid....."+this.getClass());
					HttpResponse response1=httpclient1.execute(post1);

					BufferedReader rd1= new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
					if((line1=rd1.readLine())!=null)
					{
						logger.debug("post data ppid response..."+line1+"....class name..."+this.getClass());
						ppid=new JSONArray(line1);

						for (int i = 0; i < ppid.length(); ++i) {
							JSONObject rec = ppid.getJSONObject(i);
							ppmasterid = rec.getInt("masteridindex");
						}
					}

					jsonobj.put("ppId", ppmasterid);

					//request to get tally masters id
					DefaultHttpClient httpclient2= new DefaultHttpClient();
					HttpPost post2= new HttpPost("http://localhost:9090/perpetuity/pptallymapping/gettallymasteridnames");
					StringEntity input2= new StringEntity("{\"cmpId\":\"" + cmpId + "\",\"masterType\":\"" + Mastertype + "\",\"tallyMasterName\":\"" + tallymastername + "\"}");					
					input2.setContentType("application/json");
					post2.setEntity(input2);
					logger.debug("Posting excel data to get ppid....."+this.getClass());
					HttpResponse response2=httpclient2.execute(post2);

					BufferedReader rd2= new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
					if((line2=rd2.readLine())!=null)
					{
						logger.debug("post data ppid response..."+line2+"....class name..."+this.getClass());
						masterid=new JSONArray(line2);

						for (int i = 0; i < masterid.length(); ++i) {
							JSONObject rec1 = masterid.getJSONObject(i);
							tallymasterid = rec1.getInt("masterId");
						}
					}
					jsonobj.put("tallyMasterId", tallymasterid);					

					jsonarr.put(jsonobj);		
				}				
			}

			// Create the JSON.

			logger.debug("Json String...."+jsonarr.toString()+"...class name...."+this.getClass());

			workbook.close();

			//Call the service and import the data

			for (int i = 0; i < jsonarr.length(); i++) {

				DefaultHttpClient httpclient3= new DefaultHttpClient();
				HttpPost post3= new HttpPost("http://localhost:9090/perpetuity/pptallymapping/insert");
				StringEntity input3= new StringEntity(jsonarr.getString(i));
				input3.setContentType("application/json");
				post3.setEntity(input3);
				logger.debug("Posting excel data to ppmaster add service....."+this.getClass());
				HttpResponse response3=httpclient3.execute(post3);

				BufferedReader rd3= new BufferedReader(new InputStreamReader(response3.getEntity().getContent()));
				while((line3=rd3.readLine())!=null)
				{
					logger.debug("post data response..."+line3+"....class name..."+this.getClass());
					line4=line3;
				}
			}

		} catch (IOException | JSONException e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}

		return line4;
	}
}
