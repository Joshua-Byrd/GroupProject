package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;

public interface Reader<E> {
	
	enum State {INITIAL, QUOTE, ESCAPE_QUOTE, TEXT_DATA};

	List<E> returnRecordsList() throws FileNotFoundException, NumberFormatException, IOException;
	
	default String[] readRow(BufferedReader br) throws IOException {
		
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
                        values.add("");
                        break;
                    case('"'):
                        state = State.QUOTE;
                        break;
                    default:
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
                    case('"'):
                        state = State.ESCAPE_QUOTE;
                        break;
                    default:
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
                        return values.toArray(new String[values.size()]);
                    case (','):
                        values.add(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                        state = State.INITIAL;
                        break;
                    case ('"'):  
                        stringBuilder.append((char)currentChar);
                        state = State.QUOTE;
                        break;
                }
               break;
            }
        }
    }
}
