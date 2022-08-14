package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.PopulationData;

public class PopulationReader implements Reader {
	
	private File fileName;
    private BufferedReader br;

	private Logger l = Logger.getInstance();

    private List<PopulationData> populationObjectList;
    
    public PopulationReader(File fileName) throws IOException {
        this.fileName = fileName;
        this.populationObjectList = new ArrayList<PopulationData>();
        br = new BufferedReader(new FileReader(fileName));
    }

	@Override
	public List returnRecordsList() throws IOException {
		
		//log the file after opening for reading
		l.log(System.currentTimeMillis() + " " + fileName.getName());
		
		//read first line to understand the state of the columns	
		String[] buffer = readRow(br);      
        int populationIndex = 0, zipCodeIndex = 0;
        
        for (int i=0; i<buffer.length; i++) {
        	if(buffer[i].equalsIgnoreCase("population")) {populationIndex = i;}
        	
            if(buffer[i].equalsIgnoreCase("zip_code")) {zipCodeIndex = i;}
        }
         		
        int zipCode;
        int population;
        
        String[] buff;
        String zipString;
        
        	while((buff = readRow(br)) != null) { 			
						
			if (buff[zipCodeIndex].matches("^[0-9]{5}")
					&& isValidPopulationFigure(buff[populationIndex])) {
				
				try {
					population = Integer.parseInt(buff[populationIndex]); 
				} catch (Exception e) {
					continue; 
				}
			
				try {
					zipString = (String) buff[zipCodeIndex].substring(0, buff[zipCodeIndex].length());
					zipCode = Integer.parseInt(zipString);

				} catch (Exception e){
					continue; 
				}
				
				PopulationData pd = new PopulationData(zipCode, population);
				
				populationObjectList.add(pd);
			}  
			
		
        	}
        	
		return populationObjectList;
	}

	private boolean isValidPopulationFigure(String populationAsString) {
		try {
	        int populationAsInt = Integer.parseInt(populationAsString);
	        return true;
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	}
	

}
