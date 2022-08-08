package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.COVIDReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        /*
            What does main need to do?
            1. validate files
            2. instantiate readers based on command line args
            3. pass readers to processor (processor has methods to retrieve data)
            4. pass processor to UserInterface
            5. instantiate logger
            6. run ui.start()

        */

        //validate arguments and return Map<String, File>
        //use the map in setUpProcessor to instantiate Readers and pass to processor
        //return processor
        Processor processor = setUpProcessor(validateArguments(args));
        Logger l = Logger.getInstance();
        UserInterface ui = new UserInterface(processor);
        ui.start();
    }

    /**
     * Takes in the array of arguments from main, validates each and returns an array of Files
     * to be passed to other methods.
     * @param args an array of String command line arguments
     * @return an array of File objects.
     */
    public static Map<String, File> validateArguments(String[] args) throws FileNotFoundException, AccessDeniedException {


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
                throw new FileNotFoundException("Log file not found.");
            } else if (!logFile.canRead()) {
                throw new AccessDeniedException("Log file cannot be read.");
            }

        }

        return fileMap;
    }

    public static Processor setUpProcessor(Map<String, File> fileMap){
        Processor processor = new Processor();

        for(Map.Entry<String, File> e: fileMap.entrySet()) {
            if (e.getKey().equals("covidDataFile")) {
                processor.setCovidReader(new COVIDReader(e.getValue()));
            } else if (e.getKey().equals("popDataFile")) {
                processor.setPopulationReader(new PopulationReader(e.getValue()));
            } else if (e.getKey().equals("propDataFile")) {
                processor.setPropertyReader(new PropertyReader(e.getValue()));
            }
        }

        return processor;
    }






}
