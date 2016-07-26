/**
 * 
 */
package com.goeuro.rest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeuro.model.CityInfo;
/**
 * @author FAISAL
 *
 */
@SuppressWarnings("deprecation")
public class GoEuroRestClient {
	
	private static final String URL = "http://api.goeuro.com/api/v2/position/suggest/en/";


	public static void getCityInfo(String cityName,String ouptuPath) throws ParseException, IOException {
	
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		HttpUriRequest req = new HttpGet(URL + cityName);
		HttpResponse httpResponse = httpclient.execute(req);
		HttpEntity entity = httpResponse.getEntity();
			
			if ( entity != null && httpResponse.getStatusLine().getStatusCode()== 200) {
				
				writCSVResult(entity.getContent(),cityName,ouptuPath);
				
			}
	}
	
	
	private static void writCSVResult(InputStream jsonResult,String word,String outPath) throws JsonParseException, JsonMappingException,FileNotFoundException,IOException{
		 ObjectMapper mapper = new ObjectMapper();
		 mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		 mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		 mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		 mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		 byte[] cont = new byte[1024*8];
		 jsonResult.read(cont);
		
		 CityInfo[] results =  mapper.readValue(StringUtils.newStringUtf8(cont), CityInfo[].class);
		
		 PrintWriter out = new PrintWriter(new File(outPath));
		
		
		for (CityInfo c : results) {
			
			String output = c.getId()+","+c.getName()+","+c.getType()+","+c.getGeoPosition().getLongitude()+","+c.getGeoPosition().getLatitude();
			out.println(output);
			out.flush();
		}

		System.out.println("Result file : "+outPath);
		out.close();
		
		
	}
	
	
	


}
