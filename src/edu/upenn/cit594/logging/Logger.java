package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {

    //default log file. If a log file is passed in as a command line argument, logFile
    //gets set with the setLogFile method called in validateArguments in Main.  If no log
    //file is passed in, an error is logged to System.err
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
        if (logFile.equals(System.err)) {
            //do not close System.err, just set output to given file
            out = new PrintWriter(new FileOutputStream(newFile, true), true);
        }
        out.close();
        out = new PrintWriter(new FileOutputStream(newFile, true), true);
    }

}

