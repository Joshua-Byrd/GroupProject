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
    
    enum State {INITIAL, QUOTE, INNER_QUOTE, ESCAPE_QUOTE, TEXT_DATA};

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
        String[] buffer = readRow();
        
//        for(String b: buffer) {
//        	System.out.println(b);
//        }

                
        int zipIndex = 0, partiallyVaccinatedIndex = 0, fullyVaccinatedIndex = 0, timeStampIndex = 0, deathsIndex = 0;
        
        for (int i=0; i<buffer.length; i++) {
        	if(buffer[i].equalsIgnoreCase("etl_timestamp")) {timeStampIndex = i;
        	//System.out.println("timestamp" + timeStampIndex);
        	}
            if(buffer[i].equalsIgnoreCase("zip_code")) {zipIndex = i;
            //System.out.println("zip" + zipIndex);
            }
            if(buffer[i].equalsIgnoreCase("partially_vaccinated")) {partiallyVaccinatedIndex = i;
            //System.out.println("pvaxx" + partiallyVaccinatedIndex);
            }
            if(buffer[i].equalsIgnoreCase("fully_vaccinated")) {fullyVaccinatedIndex = i;
            //System.out.println("fvaxx" + fullyVaccinatedIndex);
            }
            if(buffer[i].equalsIgnoreCase("deaths")) {deathsIndex = i;
            //System.out.println("deaths" + deathsIndex);
            }
        }

//        System.out.println("Headers:");
//        for (String s: buffer) {
//            System.out.println(s);
//        }
        
        String timeStamp;
        int zipCode;
        int partiallyVaccinated;
        int fullyVaccinated;
		int deaths;
        
        String[] buff;

		while((buff = readRow()) != null) {

					timeStamp = buff[timeStampIndex];
//					System.out.println(timeStamp);
//					System.out.println(buff[zipIndex]);
                    try {
                        zipCode = Integer.parseInt(buff[zipIndex]);
                    } catch (Exception e) {
                        zipCode = 0;
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

          //PRINTS FULL LIST OF COVIDDATA OBJECTS FOR TESTING - CAN REMOVE WHEN FINISHED
//        System.out.println("objectList size = " + objectList.size());
//        for (CovidData c: objectList){
//            System.out.println(c);
//        }
		
		
		return objectList;
				
       }

	public String[] readRow() throws IOException {
		
        int currentChar = 0;
        //get a character from reader

        //initialize arraylist to store values
        List<String> values = new LinkedList<>();

        //initialize stringBuilder to build each value
        StringBuilder stringBuilder = new StringBuilder();

        //set state to initial for beginning of line
        State state = State.INITIAL;

        while (true) {
        	if (currentChar == -1) {return null;} //end of file reached
			currentChar = br.read();
			
            //check state of the line
            switch (state) {
                case INITIAL:
                    switch(currentChar) {
                        case('\r'):
                            break;
                        case('\n'):
                            values.add(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                            return values.toArray(new String[values.size()]);
                        case(','):
                            //add empty string if a comma is found initially
                            values.add("");
                        	//CHECKED
                            break;
                        case('"'):
                            //change state if a double quote is encountered
                            state = State.QUOTE;
                            break;
                        default:
                            //otherwise, append character to stringBuilder
                        	//CHECKED
                            stringBuilder.append((char)currentChar);
                            state = State.TEXT_DATA;
                            break;
                    }
                    break;
                case TEXT_DATA:
                    switch(currentChar) {
                        case('\r'):
                            break;
                        case('\n'):
                            values.add(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                            return values.toArray(new String[values.size()]);
                        case(','):
                            //if a comma is reached, add the word to the list and reset stringBuilder and state
                            values.add(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                            state = State.INITIAL;
                            break;
                        default:
                            stringBuilder.append((char)currentChar);
                            break;
                    }
                    break;
                case QUOTE:
                    switch(currentChar) {
                        case('\r'):
                            break;
                        case('\n'):
                            stringBuilder.append((char)currentChar);
                            break;
                        //if state is QUOTE and another QUOTE is encountered, change state to INNER_QUOTE,
                        //otherwise add the current character.
                        case('"'):
                            state = State.ESCAPE_QUOTE;
                            break;
                        default:
                			//System.out.println((char)currentChar);
                            stringBuilder.append((char)currentChar);
                            break;
                    }
                    break;
                case ESCAPE_QUOTE:
                    switch (currentChar) {
                        case ('\r'):
                            break;
                        case ('\n'):
                            values.add(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
//                            System.out.println(values);
                            return values.toArray(new String[values.size()]);
                        case (','):
                        	//System.out.println(stringBuilder.toString());
                        	//System.out.println("size before adding" + values.size());

                        	//System.out.println("size of values after adding" + " " + stringBuilder.toString() + " " + values.size());
                            values.add(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                            state = State.INITIAL;
                            break;
                        case ('"'):
                            state = State.INNER_QUOTE;
                            break;
                    }
                    break;
                case INNER_QUOTE:
                    switch (currentChar) {
                        case('\r'):
                            break;
                        case('\n'):
                            values.add(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                            return values.toArray(new String[values.size()]);
                        case(','):
                            values.add(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                            state = State.INITIAL;
                            break;
                        case('"'):
                            stringBuilder.append((char)currentChar);
                            state = State.QUOTE;
                            break;
                    }
//                    System.out.println("size of values at breaking" + values.size());
//                    for (String v: values) {
//                    	System.out.println(v);
//                    }
                    
                    break;
            }
        }
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

