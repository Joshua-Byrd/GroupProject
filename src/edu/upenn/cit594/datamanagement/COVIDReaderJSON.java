package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class COVIDReaderJSON<E> implements Reader  {

	private File fileName;
	
	private long zipCode;
	private String timeStamp;
	private long pvaxx;
	private long fvaxx;
	

	private Logger l = Logger.getInstance();
	private List<CovidData> objectList;


	public COVIDReaderJSON(File fileName) throws IOException {
		this.fileName = fileName;
		this.objectList = new ArrayList<CovidData>();
	}

	@Override
	public List returnRecordsList() throws FileNotFoundException, NumberFormatException, IOException{
		
		Object obj;	
		
		try {
			obj = new JSONParser().parse(new FileReader(fileName));
			JSONArray jo = (JSONArray) obj;	
			List<CovidData> cd = new ArrayList<CovidData>();
			for (int i=0; i< jo.size(); i++) {
				
				JSONObject obj2 = (JSONObject)jo.get(i);	
				
				if (obj2 != null) {
					
				zipCode =  (long) obj2.get("zip_code");
				
				if (String.valueOf(zipCode).length() == 5) {
					
					timeStamp = (String) obj2.get("etl_timestamp");
					
					if (isTimeStampValidFormat(timeStamp)) {					
						
						try {
							pvaxx = (long) obj2.get("partially_vaccinated"); 
						} catch (Exception e) {
							pvaxx = 0;
						}
						
						try {
							fvaxx = (long) obj2.get("fully_vaccinated");	
						} catch (Exception e) {
							fvaxx = 0;
						}
												
						CovidData covidData = new CovidData((int)zipCode, timeStamp, (int)pvaxx, (int) fvaxx);
						
						objectList.add(covidData);
					}
							
				}

			}
		}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return objectList;
		
	}

	private boolean isTimeStampValidFormat(String timeStamp) {
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try {
			format.parse(timeStamp.substring(1, timeStamp.length()-1));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return true;
	 }
}		

