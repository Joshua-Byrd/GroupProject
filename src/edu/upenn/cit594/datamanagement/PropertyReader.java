package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.PropertyValueData;

public class PropertyReader implements Reader {
	
	private File fileName;
	private BufferedReader br;

	private Logger l = Logger.getInstance();

    private List<PropertyValueData> propertyObjectList;
    
    public PropertyReader(File fileName) throws IOException {
        this.fileName = fileName;
        this.propertyObjectList = new ArrayList<PropertyValueData>();
        br = new BufferedReader(new FileReader(fileName));
    }

	@Override
	public List returnRecordsList() throws IOException {
		if (l.getLogFile() == null) {
			l.logErr(System.currentTimeMillis() + " " + fileName.getName());
		} else {
			l.log(System.currentTimeMillis() + " " + fileName.getName());
		}

		//read first line to understand the state of the columns	
		String[] buffer = readRow(br);       
        int marketValueIndex = 0, livableAreaIndex = 0, zipCodeIndex = 0;
        
        for (int i=0; i<buffer.length; i++) {
       
        	if(buffer[i].equalsIgnoreCase("market_value")) {marketValueIndex = i;}
            if(buffer[i].equalsIgnoreCase("total_livable_area")) {livableAreaIndex = i;}
            if(buffer[i].equalsIgnoreCase("zip_code")) {zipCodeIndex = i;}
        }
        
        int zipCode;
        String marketValue; //string because page 5 says the market value and livable area can be non-numeric, and if have to ignore them during calculation but still read them
        String livableArea;
        
        String[] buff;
		int count = 1;
        while((buff = readRow(br)) != null) { 
        	        							
			if (isZipCodeValid(buff[zipCodeIndex])) {
				
				marketValue = buff[marketValueIndex]; //include malformed data
			
				livableArea = buff[livableAreaIndex];
				
				try {
					zipCode = Integer.parseInt(buff[zipCodeIndex].substring(0, 5));
				} catch (Exception e) {
					continue;
				}
			
				PropertyValueData pvd = new PropertyValueData(marketValue, livableArea, zipCode);
				
				propertyObjectList.add(pvd);			
			}
        }
        
//        for (PropertyValueData o: propertyObjectList) {
//        	System.out.println(o.getZipCode());
//        }

		return propertyObjectList;
		}

	private boolean isZipCodeValid(String zipCode) {
		
		
		
		if (zipCode.length() < 5) {
			return false;
		}
		
		else if (zipCode.matches("^[0-9]{5}")) {
			return true;
		} return false;
	}
}
