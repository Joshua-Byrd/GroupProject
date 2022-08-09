package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.util.PopulationData;

public class PopulationReader implements Reader {
	
	private File fileName;

    private List<PopulationData> populationObjectList;
    
    public PopulationReader(File fileName) {
        this.fileName = fileName;
        this.populationObjectList = new ArrayList<PopulationData>();
    }

	@Override
	public List returnRecordsList() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		//read first line to understand the state of the columns	
        String buffer = br.readLine();
        String[] lineArray = buffer.split(",", -1);        
        int populationIndex = 0, zipCodeIndex = 0;
        
        for (int i=0; i<lineArray.length; i++) {
        	if(lineArray[i].equalsIgnoreCase("\"population\"")) {populationIndex = i;
        	}
        	
            if(lineArray[i].equalsIgnoreCase("\"zip_code\"")) {zipCodeIndex = i;
            }
        }
 		
        int zipCode;
        int population;
        
        	while((buffer = br.readLine()) != null) { 
        	
			String[] populationRecordArray = buffer.split(",", -1);
			
						
			if (String.valueOf(populationRecordArray[zipCodeIndex].subSequence(1, populationRecordArray[zipCodeIndex].length()-1)).length() == 5 
					&& isValidPopulationFigure(populationRecordArray[populationIndex])) {
								
				try {
					population = Integer.parseInt(populationRecordArray[populationIndex]); 
				} catch (Exception e) {
					population = 0; //if field is empty
				}
			
				try {
					buffer = (String) populationRecordArray[zipCodeIndex].subSequence(1, populationRecordArray[zipCodeIndex].length()-1);
					zipCode = Integer.parseInt(buffer);

				} catch (Exception e){
					zipCode = 0; 
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
