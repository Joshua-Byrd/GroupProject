package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;

public class COVIDReaderCSV implements Reader{
	
	private File fileName;
    
    private BufferedReader br;
    

	private Logger l = Logger.getInstance();
    private List<CovidData> objectList;
    
    public COVIDReaderCSV(File fileName) throws IOException {
        this.fileName = fileName;
        this.objectList = new ArrayList<CovidData>();
        br = new BufferedReader(new FileReader(fileName));

    }

	public List returnRecordsList() throws NumberFormatException, IOException, FileNotFoundException  {

		//log the file after opening for reading
		l.log(System.currentTimeMillis() + " " + fileName.getName());

		//read first line to understand state of the columns		
        String[] buffer = readRow(br);
             
        int zipIndex = 0, partiallyVaccinatedIndex = 0, fullyVaccinatedIndex = 0, timeStampIndex = 0, deathsIndex = 0;
        
        for (int i=0; i<buffer.length; i++) {
        	if(buffer[i].equalsIgnoreCase("etl_timestamp")) {timeStampIndex = i;}
            if(buffer[i].equalsIgnoreCase("zip_code")) {zipIndex = i;}
            if(buffer[i].equalsIgnoreCase("partially_vaccinated")) {partiallyVaccinatedIndex = i;}
            if(buffer[i].equalsIgnoreCase("fully_vaccinated")) {fullyVaccinatedIndex = i;}
            if(buffer[i].equalsIgnoreCase("deaths")) {deathsIndex = i;}
        }

        
        String timeStamp;
        int zipCode;
        int partiallyVaccinated;
        int fullyVaccinated;
		int deaths;
        
        String[] buff;

		while((buff = readRow(br)) != null) {

					timeStamp = buff[timeStampIndex];
					
                    try {
                        zipCode = Integer.parseInt(buff[zipIndex]);
                    } catch (Exception e) {
                        break;
                    }
                    try {
                        deaths = Integer.parseInt(buff[deathsIndex]);
                    } catch (Exception e) {
                        deaths = 0;
                    }


				//ignoring the record if the statement below fails
			if (isTimeStampValidFormat(timeStamp) && buff[zipIndex].length() == 5) {

//				System.out.println("coming here");

                    try {
                        partiallyVaccinated = Integer.parseInt(buff[partiallyVaccinatedIndex]);
                    } catch (Exception e) {
                        partiallyVaccinated = 0;
                    }

                    try {
                        fullyVaccinated = Integer.parseInt(buff[fullyVaccinatedIndex]);
                    } catch (Exception e) {
                        fullyVaccinated = 0;
                    }

					CovidData cd = new CovidData(zipCode, timeStamp, partiallyVaccinated, fullyVaccinated, deaths);

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

