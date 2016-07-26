package com.goeuro.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;

import com.goeuro.rest.GoEuroRestClient;

public class GoEuroMain {
	
	public static void main(String[] args) {
		
		
		if (args.length !=2) {
			System.out.println("Please enter city name and output file path in arguments.");
			System.exit(0);
		}
	
		if (StringUtils.isNumeric(args[0])) {
			System.err.println("Please enter valid city name.");
			System.exit(0);
		}
		
		if (!args[1].endsWith(".csv")) {
			System.err.println("Please enter valid output file format.");
			System.exit(0);
		}
		
		
		try {
			GoEuroRestClient.getCityInfo(args[0],args[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("Internal system problem. : "+e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Invalid output file path : "+e.getMessage());
		} catch (IOException e) {
			
			System.err.println("Output corrupted : "+e.getMessage());
		}
	}

}
