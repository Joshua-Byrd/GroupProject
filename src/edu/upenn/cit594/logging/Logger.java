package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    //default log file. If a log file is passed in as a command line argument, logFile
    //gets set with the setLogFile method called in validateArguments in Main.  If no log
    //file is passed in, an error is logged to System.err, and an exception is thrown.
	
    private File logFile = null;
    private PrintWriter logFileWriter;

    private Logger() {};

    private static Logger instance = new Logger();

    /**
     * Returns a new instance of the Logger only if one does not exist. Otherwise, returns the
     * existing instance in accordance with the Singleton pattern
     * @return instance of Logger
     * @throws IOException if file is not found
     */
    public static Logger getInstance() throws IOException {      
        return instance;
    }

    /**
     * Writes the given message to the current logfile
     * @param msg to be written to the log file
     */
    public void log(String msg){
    	if (logFile == null) {
    		System.err.println(msg);
    	} else {
    		logFileWriter.println(msg);
    		logFileWriter.flush();
    	}
    }

    /**
     * Closes the current log file and opens a new, given log file.
     * @param newFile to be opened and written to
     * @throws IOException if the file cannot be opened
     */
    public void setLogFile(String newFile) throws IOException {
    	    	
        if (logFile == null) {
            logFile = new File(newFile);
            //if setting for the first time or if currently outputting
        	logFileWriter = new PrintWriter(new FileOutputStream(logFile, true), true);
        } else {
            //if logfile has been set, close old logfile and set new logfile
        	logFileWriter.close();
            logFile = new File(newFile);
        	logFileWriter = new PrintWriter(new FileOutputStream(logFile, true), true);
        }
    }
 
    public void closePrevious() {
        if (logFile != null) {
            logFileWriter.close();
        }
    }

	public File getLogFile() {
		return logFile;
	}
    
}

