package edu.upenn.cit594;

import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        UserInterface ui = new UserInterface(new Processor());
        ui.runUI(new Scanner(System.in));



    }

    /**
     * Takes in the array of arguments from main, validates each and returns an array of Files
     * to be passed to other methods.
     * @param args an array of String command line arguments
     * @return an array of File objects.
     */
    public ArrayList<File> validateArguments(String[] args) throws FileNotFoundException, AccessDeniedException {


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

        ArrayList<File> fileArrayList = new ArrayList<>();


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
        }

        if (propFile != null) {
            propDataFile = new File(propFile);
            if  (!propDataFile.exists()) {
                throw new FileNotFoundException("Covid file not found.");
            } else if (!propDataFile.canRead()) {
                throw new AccessDeniedException("Covid file cannot be read.");
            }
        }

        if (popFile != null) {
            popDataFile = new File(popFile);
            if  (!popDataFile.exists()) {
                throw new FileNotFoundException("Covid file not found.");
            } else if (!popDataFile.canRead()) {
                throw new AccessDeniedException("Covid file cannot be read.");
            }
        }

        if (logFileArg != null) {
            logFile = new File(logFileArg);
            if  (!logFile.exists()) {
                throw new FileNotFoundException("Covid file not found.");
            } else if (!logFile.canRead()) {
                throw new AccessDeniedException("Covid file cannot be read.");
            }

        }

        return fileArrayList;

    }





}
