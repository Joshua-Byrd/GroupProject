package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.COVIDReaderCSV;
import edu.upenn.cit594.datamanagement.COVIDReaderJSON;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        Processor processor = setUpProcessor(validateArguments(args));
        UserInterface ui = new UserInterface(processor);
        ui.start();
    }

    /**
     * Takes in the array of arguments from main, validates each and returns a Hashmap, mapping filenames to
     * File objects, to be used by the setUpProcessor() method.
     * @param args an array of String command line arguments
     * @return a map of file names/file objects.
     */
    public static Map<String, File> validateArguments(String[] args) throws IOException {

        Logger l = Logger.getInstance();

        /******* Check that arguments are all well-formed *******/

        //incoming args
        String covidFile = null;
        String propFile = null;
        String popFile = null;
        String logFileArg = null;

        File covidDataFile;
        File propDataFile;
        File popDataFile;
        File logFile;

        Map<String, File> fileMap = new HashMap<>();


        Pattern argsPattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");

        for (String arg: args) {
            Matcher matcher = argsPattern.matcher(arg);
            if (matcher.find()) {
                //if a matching argument is found, parse into name and value
                String argName = matcher.group("name");
                String argValue = matcher.group("value");

                switch (argName) {
                    case("covid"):
                        if(covidFile != null) {
                            throw new IllegalArgumentException("Covid data file declared twice.");
                        } else {
                            covidFile = argValue;
                        }
                        break;
                    case("properties"):
                        if(propFile != null) {
                            throw new IllegalArgumentException("Properties data file declared twice.");
                        } else {
                            propFile = argValue;
                        }
                        break;
                    case("population"):
                        if(popFile != null) {
                            throw new IllegalArgumentException("Population data file declared twice.");
                        } else {
                            popFile = argValue;
                        }
                        break;
                    case("log"):
                        if (logFileArg != null) {
                            throw new IllegalArgumentException("log file declared twice.");
                        } else {
                            logFileArg = argValue;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Argument name must be 'covid', 'properties'," +
                                " 'population', or 'log'.");
                }

            } else {
                //throw exception if any argument is not in the correct form
                throw new IllegalArgumentException("All arguments must be of the form: --name=value");
            }
        }
        /******* Create files and validate *******/

        if (covidFile != null) {
            covidDataFile = new File(covidFile);
            if  (!covidDataFile.exists()) {
                throw new FileNotFoundException("Covid file not found.");
            } else if (!covidDataFile.canRead()) {
                throw new AccessDeniedException("Covid file cannot be read.");
            }
            fileMap.put("covidDataFile", covidDataFile);
        }

        if (propFile != null) {
            propDataFile = new File(propFile);
            if  (!propDataFile.exists()) {
                throw new FileNotFoundException("Property file not found.");
            } else if (!propDataFile.canRead()) {
                throw new AccessDeniedException("Property file cannot be read.");
            }
            fileMap.put("propDataFile", propDataFile);
        }

        if (popFile != null) {
            popDataFile = new File(popFile);
            if  (!popDataFile.exists()) {
                throw new FileNotFoundException("Population file not found.");
            } else if (!popDataFile.canRead()) {
                throw new AccessDeniedException("Population file cannot be read.");
            }
            fileMap.put("popDataFile", popDataFile);
        }

        if (logFileArg != null) {
            logFile = new File(logFileArg);
            if  (!logFile.exists()) {
                l.setLogFile(System.err.toString());
                l.log("Log file new found.");
                throw new FileNotFoundException("Log file not found.");
            } else if (!logFile.canRead()) {
                throw new AccessDeniedException("Log file cannot be read.");
            }
        } else {
            l.setLogFile(System.err.toString());
            l.log("No log file was passed to command line.");
            throw new FileNotFoundException("Log file not found.");
        }

        return fileMap;
    }

    /**
     * Takes in a map of file names/File objects, instantiates a Reader for each File and
     * returns a new Processor with the given Readers.
     * @param fileMap of file names and objects
     * @return a new Processor with the given Readers
     */
    public static Processor setUpProcessor(Map<String, File> fileMap) throws IOException {
        Logger l = Logger.getInstance();
        Processor processor = new Processor();
        logCommandLineArgs(fileMap, l);

        for(Map.Entry<String, File> e: fileMap.entrySet()) {
            if (e.getKey().equals("covidDataFile")) {
                if (e.getValue().getName().split("\\.(?=[^\\.]+$)")[1].toLowerCase().equals("csv")) {
                    processor.setCovidReader(new COVIDReaderCSV(e.getValue()));
                } else if (e.getValue().getName().split("\\.(?=[^\\.]+$)")[1].toLowerCase().equals("json")) {
                    processor.setCovidReaderJSON(new COVIDReaderJSON(e.getValue()));
                } else {
                    throw new IllegalArgumentException("Covid data file must be a valid .txt or .json file.");
                }
            } else if (e.getKey().equals("popDataFile")) {
                processor.setPopulationReader(new PopulationReader(e.getValue()));
            } else if (e.getKey().equals("propDataFile")) {
                processor.setPropertyReader(new PropertyReader(e.getValue()));
            }
        }

        return processor;
    }

    /**
     * Creates a string containing all command line arguments and logs them to the
     * current log file.
     * @param fileMap a map of filename to files
     * @param l logger
     */
    public static void logCommandLineArgs(Map<String, File> fileMap, Logger l) {
        StringBuilder argsString = new StringBuilder();
        for (Map.Entry<String, File> entry: fileMap.entrySet()) {
            File f = entry.getValue();
            argsString.append(f.getName()).append(" ");
        }

        l.log(System.currentTimeMillis() + " " + argsString);
    }






}
