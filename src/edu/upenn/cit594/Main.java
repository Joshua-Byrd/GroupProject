package edu.upenn.cit594;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {


        String covidDataFile = null;
        String propDataFile = null;
        String popDataFile = null;
        String logFile = null;

        Pattern argsPattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");

        for (String arg: args) {
            Matcher matcher = argsPattern.matcher(arg);
            if (matcher.find()) {
                //if a matching argument is found, parse into name and value
                String argName = matcher.group("name");
                String argValue = matcher.group("value");

                switch (argName) {
                    case("covid"):
                        if(covidDataFile != null) {
                            throw new IllegalArgumentException("Covid data file declared twice.");
                        } else {
                            covidDataFile = argValue;
                        }
                        break;
                    case("properties"):
                        if(propDataFile != null) {
                            throw new IllegalArgumentException("Properties data file declared twice.");
                        } else {
                            propDataFile = argValue;
                        }
                        break;
                    case("population"):
                        if(popDataFile != null) {
                            throw new IllegalArgumentException("Population data file declared twice.");
                        } else {
                            popDataFile = argValue;
                        }
                        break;
                    case("log"):
                        if (logFile != null) {
                            throw new IllegalArgumentException("log file declared twice.");
                        } else {
                            logFile = argValue;
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
    }

}
