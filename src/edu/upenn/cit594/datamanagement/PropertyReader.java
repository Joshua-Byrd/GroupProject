package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.PropertyValueData;

public class PropertyReader implements Reader {
	
	private File fileName;

    private List<PropertyValueData> propertyObjectList;
    
    public PropertyReader(File fileName) {
        this.fileName = fileName;
        this.propertyObjectList = new ArrayList<PropertyValueData>();
    }

	@Override
	public List returnRecordsList() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		//read first line to understand the state of the columns	
        String buffer = br.readLine();
        String[] lineArray = buffer.split(",", -1);        
        int marketValueIndex = 0, livableAreaIndex = 0, zipCodeIndex = 0;
        
        for (int i=0; i<lineArray.length; i++) {
       
        	if(lineArray[i].equalsIgnoreCase("market_value")) {marketValueIndex = i;}
            if(lineArray[i].equalsIgnoreCase("total_livable_area")) {livableAreaIndex = i;}
            if(lineArray[i].equalsIgnoreCase("zip_code")) {zipCodeIndex = i;}
        }
        
        int zipCode;
        String marketValue; //string because page 5 says the market value and livable area can be non-numeric, and if have to ignore them during calculation but still read them
        String livableArea;
		
        while((buffer = br.readLine()) != null) { 
        	
			String[] propertyRecordArray = buffer.split(",", -1);
						
			if (isZipCodeValid(propertyRecordArray[zipCodeIndex])) {

				marketValue = propertyRecordArray[marketValueIndex]; //include malformed data
			
				livableArea = propertyRecordArray[livableAreaIndex];
				
				zipCode = Integer.parseInt(propertyRecordArray[zipCodeIndex].substring(0, 5));
			
				PropertyValueData pvd = new PropertyValueData(marketValue, livableArea, zipCode);
				
				propertyObjectList.add(pvd);
			
			}
		
        }
		
		return propertyObjectList; //if file is empty
	}

	private boolean isZipCodeValid(String zipCode) {
		
		if (zipCode.length() < 5) {
			//System.out.println("eliminated" + zipCode); //for debugging
			return false;
		}
		
		String regex = "/(?!^\\d+$)^.+$/g"; //anything other than numerics
		
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		
		Matcher m = p.matcher(zipCode.substring(0, 5));
			
		 if(m.find()) {	//System.out.println("eliminated regex" + zipCode); //debugging

			 return false;}
		
		 return true;

	}
}
