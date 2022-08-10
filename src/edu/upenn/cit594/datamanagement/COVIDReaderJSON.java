package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CovidData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class COVIDReaderJSON<E> implements Reader  {

	private File fileName;

	private Logger l = Logger.getInstance();
	private List<CovidData> objectList;


	public COVIDReaderJSON(File fileName) throws IOException {
		this.fileName = fileName;
		this.objectList = new ArrayList<CovidData>();
	}

	@Override
	public List returnRecordsList() throws FileNotFoundException, NumberFormatException, IOException {
		return null;
	}
}
