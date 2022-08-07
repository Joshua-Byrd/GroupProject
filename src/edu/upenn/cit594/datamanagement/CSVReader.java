package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CSVReader<E> {
	
	List<E> returnRecordsList() throws FileNotFoundException, NumberFormatException, IOException;

}
