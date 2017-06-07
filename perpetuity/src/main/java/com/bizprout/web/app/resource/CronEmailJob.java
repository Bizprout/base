package com.bizprout.web.app.resource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class CronEmailJob implements Job {

	Logger logger=LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings({"resource"})
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		try {
		Properties prop = new Properties();
		InputStream inprop = null;
		
		inprop = new FileInputStream("Postgeturls.properties");

		// load a properties file
		prop.load(inprop);
		
		String baseserveraddress=prop.getProperty("baseserveraddress");
		String compurl=prop.getProperty("Cronemailjobgetallactivecompanies");
		String mastersandvoucherurl=prop.getProperty("Cronemailjobgetallmastersandvouchers");
		
		logger.debug("Executing the Scheduled Cron Job"+this.getClass());

		//Get the list of all active companies

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(baseserveraddress+compurl);
		HttpResponse response;

		String respdata=null;
		int cmpid=0;
		String line1=null;
		String clientemail=null;
		String cmpname=null;

		Email email=new Email();

		
			response = client.execute(request);

			// Get the response
			BufferedReader rd = new BufferedReader
					(new InputStreamReader(
							response.getEntity().getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				respdata=line;
			}


			JSONArray jarr=new JSONArray(respdata);

			for(int i=0; i<jarr.length();i++)
			{
				JSONObject jobj=new JSONObject(jarr.getString(i)); 
				cmpid=jobj.getInt("cmpId");

				cmpname=jobj.getString("tallyCmpName");

				JSONObject clientdto=jobj.getJSONObject("client");

				clientemail=clientdto.getString("contactEmail");

				// post and get master type data for the cmpid

				DefaultHttpClient httpclient= new DefaultHttpClient();
				HttpPost post= new HttpPost(baseserveraddress+mastersandvoucherurl);
				StringEntity input= new StringEntity("{\"cmpid\":\"" + cmpid + "\"}");
				input.setContentType("application/json");
				post.setEntity(input);
				logger.debug("Posting getMastertypeData service....."+this.getClass());
				HttpResponse resp=httpclient.execute(post);

				BufferedReader rdl= new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
				while((line=rdl.readLine())!=null)
				{
					logger.debug("post data response..."+line+"....class name..."+this.getClass());
					line1=line;

				}

				JSONArray syncedata=new JSONArray(line1);

				if(syncedata.length()>0)
				{
					if(syncedata.toString().contains("\"synctype\":\"Master\""))
					{
						if(syncedata.toString().contains("\"synctype\":\"Voucher\""))
						{

						}
						else
						{
							email.sendClientEmailSyncError(clientemail, "Voucher", cmpname);
						}
					}
					else
					{
						email.sendClientEmailSyncError(clientemail, "Master", cmpname);
					}
				}
				else
				{
					email.sendClientEmailSyncError(clientemail, "Both", cmpname);
				}
			}

		}catch (Exception e) {
			logger.error(e.getMessage()+"..."+this.getClass());
		}


	}

}
