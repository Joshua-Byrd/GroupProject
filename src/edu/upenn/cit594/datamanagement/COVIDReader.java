package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.util.CovidData;

public class COVIDReader implements Reader{
	
	private File fileName;

    private List<CovidData> objectList;
    
    private final String timeStampPattern = "YYYY-MM-DD hh:mm:ss";

    public COVIDReader(File fileName) {
        this.fileName = fileName;
        this.objectList = new ArrayList<CovidData>();
    }

	public List returnRecordsList() throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		//read first line to understand state of the columns
		
        String buffer = br.readLine();
        String[] lineArray = buffer.split(",");        
        int zipIndex = 0, partiallyVaccinatedIndex = 0, fullyVaccinatedIndex = 0, timeStampIndex = 0;
        
        for (int i=0; i<lineArray.length; i++) {
        	if(lineArray[i].equalsIgnoreCase("\"etl_timestamp\"")) {timeStampIndex = i;}
            if(lineArray[i].equalsIgnoreCase("\"zip_code\"")) {zipIndex = i;}
            if(lineArray[i].equalsIgnoreCase("\"partially_vaccinated\"")) {partiallyVaccinatedIndex = i;}
            if(lineArray[i].equalsIgnoreCase("\"fully_vaccinated\"")) {fullyVaccinatedIndex = i;}
        }
        
        String timeStamp;
        int zipCode;
        int partiallyVaccinated;
        int fullyVaccinated;
        
        while((buffer = br.readLine()) != null) { 
					String[] covidRecordArray = buffer.split(",", -1);
		            
					try {
						timeStamp = covidRecordArray[timeStampIndex];
					} catch (Exception e) {
						timeStamp = "0";
					}
					
					try{
						zipCode = Integer.parseInt(covidRecordArray[zipIndex]);
		            } catch(Exception e) {
		            	zipCode = 0;
		            }
				
			
				//ignoring the record if the statement below fails
			if (isTimeStampValidFormat(timeStamp) && covidRecordArray[zipIndex].length() == 5) {
				
					try {
						partiallyVaccinated = Integer.parseInt(covidRecordArray[partiallyVaccinatedIndex]);
					} catch (Exception e) {
						partiallyVaccinated = 0;
					}
				
					try {
						fullyVaccinated = Integer.parseInt(covidRecordArray[fullyVaccinatedIndex]);
					} catch (Exception e){
						fullyVaccinated = 0;
					}
					
					CovidData cd = new CovidData(zipCode, timeStamp, partiallyVaccinated, fullyVaccinated);
					
					objectList.add(cd);
				
					}
        }
		return objectList;
      }

	private boolean isTimeStampValidFormat(String timeStamp) {

	SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
        format.parse(timeStamp.substring(1, timeStamp.length()-1));
        return true;
    } catch (ParseException e) {
    	System.out.println("Time Stamp " + timeStamp + " is not valid according to " +
                format.toPattern() + " pattern.");
 
        return false;
    }
 }
	
}

