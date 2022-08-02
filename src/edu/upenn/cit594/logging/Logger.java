package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    //set default log file. Can be changed with setLogFile method
    private File logFile = new File("log.txt");
    private PrintWriter out;

    private Logger() throws IOException {
        out = new PrintWriter(new FileOutputStream(logFile, true));
    }

    private static Logger instance = null;

    /**
     * Returns a new instance of the Logger only if one does not exist. Otherwise, returns the
     * existing instance in accordance with the Singleton pattern
     * @return instance of Logger
     * @throws IOException if file is not found
     */
    public static Logger getInstance() throws IOException {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Writes the given message to the current logfile
     * @param msg to be written to the log file
     */
    public void log(String msg){
        out.println(msg);
        out.flush();
    }

    /**
     * Closes the current log file and opens a new, given log file.
     * @param newFile to be opened and written to
     * @throws IOException if the file cannot be opened
     */
    public void setLogFile(String newFile) throws IOException {
        out.close();
        out = new PrintWriter(new FileOutputStream(newFile, true), true);
    }

}

