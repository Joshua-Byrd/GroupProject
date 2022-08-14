package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

public interface Reader<E> {

	List<E> returnRecordsList() throws FileNotFoundException, NumberFormatException, IOException;

}
