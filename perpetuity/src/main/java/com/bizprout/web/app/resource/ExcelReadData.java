package com.bizprout.web.app.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

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

@SuppressWarnings("deprecation")
public class ExcelReadData {

	@SuppressWarnings("resource")
	public String excelReadData(String filename){

		Logger logger=LoggerFactory.getLogger(this.getClass());

		final String FILE_NAME="C:/Apache Software Foundation/Tomcat 8.0/tmpFiles/"+filename;

		Workbook workbook;

		String line="";
		String line1="";

		logger.debug("inside Excel read data class..."+this.getClass());
		try {

			FileInputStream excelfile= new FileInputStream(new File(FILE_NAME));
			workbook=new XSSFWorkbook(excelfile);
			Sheet datatypesheet= workbook.getSheetAt(0);
			Iterator<Row> iterator=datatypesheet.iterator();

			JSONArray jsonarr=new JSONArray();

			while (iterator.hasNext()) {
				Row currentrow=iterator.next();

				if(currentrow.getRowNum()>2)
				{						
					JSONObject jsonobj = new JSONObject();

					//getCellTypeEnum shown as deprecated for version 3.15
					//getCellTypeEnum ill be renamed to getCellType starting from version 4.0

					jsonobj.put("cmpid", 1);

					jsonobj.put("mastertype", currentrow.getCell(0).getStringCellValue());
					jsonobj.put("ppmastername", currentrow.getCell(1).getStringCellValue());
					jsonobj.put("ppparentname", currentrow.getCell(2).getStringCellValue());

					jsonarr.put(jsonobj);		
				}				
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
					line1=line;
				}
			}

		} catch (IOException | JSONException e) {

			e.printStackTrace();
			logger.error(e.getMessage()+"...."+this.getClass());
		}

		return line1;
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

		logger.debug("inside Excel read data class..."+this.getClass());
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

			e.printStackTrace();
			logger.error(e.getMessage()+"...."+this.getClass());
		}

		return line4;
	}
}
