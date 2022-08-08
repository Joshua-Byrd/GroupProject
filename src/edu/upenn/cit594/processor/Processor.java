package edu.upenn.cit594.processor;
import edu.upenn.cit594.datamanagement.COVIDReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.PopulationData;
import edu.upenn.cit594.util.PropertyValueData;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Processor {

    COVIDReader covidReader;
    PropertyReader propertyReader;
    PopulationReader populationReader;


    /*-----Data lists-----*/

    private ArrayList<CovidData> covidDatabase;
    private ArrayList<PropertyValueData> propertyDatabase;
    private ArrayList<PopulationData> populationDatabase;

    /*-----Memoization variables-----*/

    private int totalPopulation = 0;
    Map<Integer, Double> partialVaccinationResults = new TreeMap<>();
    Map<Integer, Double> fullVaccinationResults = new TreeMap<>();
    Map<String, Integer> avgMktValueResults = new HashMap<>();
    Map<String, Integer> avgTotLivAreaResults = new HashMap<>();
    Map<String, Integer> totMktValuePerCapitaResults = new HashMap<>();
    Map<String, Integer> customFeatureResults = new HashMap<>();

    public Processor(Reader...arr) {

    }


    /*-----Data Calculation Methods-----*/

    /**
     * Returns a list of the datasets that are currently in the program
     *
     * @return
     */
    public List<String> getAvailableDataSet() {
        return new ArrayList<>();
    }

    /**
     * Memoization method. Calculates the total population only if totalPopulation
     * has not been set. Otherwise, returns totalPopulation.
     *
     * @return totalPopulation
     */
    public int getTotalPopulation() {
        if (totalPopulation == 0) {
            //perform calculations with dataset
            //and set totalPopulation
        }
        return totalPopulation;
    }

    /**
     * Accepts a date in String form, and returns a hashmap where the keys are each of the ZIP codes in the
     * Philadelphia area and the values are the partial vaccinations per capita for that ZIP code
     *
     * @param date to search for vaccination statuses
     * @return Hashmap of ZIP codes/Vaccination status per capita
     */
    public Map<Integer, Double> getPartialVaccinationsPerCapita(String date) {
        if (partialVaccinationResults.size() == 0) {
            //perform calculations with dataset
            //and set partialVaccinationResults
        }

        return partialVaccinationResults;
    }

    /**
     * Accepts a date in String form and returns a hashmap where the keys are each of the ZIP codes in the
     * Philadelphia area and the values are the full vaccinations per capita for that ZIP code
     *
     * @param date to search vaccination statuses
     * @return HashMap of ZIP codes/vaccination
     */
    public Map<Integer, Double> getFullVaccinationsPerCapita(String date) {
        if (fullVaccinationResults.size() == 0) {
            //perform calculations and
            //set fullVaccinationResults
        }

        return fullVaccinationResults;
    }

    public int getAvgMarketValue(String zipcode) {
        if (avgMktValueResults.containsKey(zipcode)) {
            return (avgMktValueResults.get(zipcode));
        } else {
            //calculate average market value and add
            //to avgMktValueResults map
            //return result
            return 0;
        }
    }

    ;

    public int getAvgTotLivableArea(String zipcode) {
        if (avgTotLivAreaResults.containsKey(zipcode)) {
            return avgTotLivAreaResults.get(zipcode);
        } else {
            //calculate average total liveable area and
            //add result to avgTotLivAreaResults
            //return result
            return 0;
        }
    }

    public int getTotalMarketValue(String zipcode) {
        if (totMktValuePerCapitaResults.containsKey(zipcode)) {
            return totMktValuePerCapitaResults.get(zipcode);
        } else {
            //calculate total market value per capita
            //and add to totMktValuePerCapitaResults
            //return result
            return 0;
        }
    }

    public int getCustomFeature(String input) {
        if (customFeatureResults.containsKey(input)) {
            return customFeatureResults.get(input);
        } else {
            //calculate the custom feature and add
            //to customFeatureResults
            //return result
            return 0;
        }
    }

    public COVIDReader getCovidReader() {
        return covidReader;
    }

    public void setCovidReader(COVIDReader covidReader) {
        this.covidReader = covidReader;
    }

    public PropertyReader getPropertyReader() {
        return propertyReader;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    public edu.upenn.cit594.datamanagement.PopulationReader getPopulationReader() {
        return populationReader;
    }

    public void setPopulationReader(PopulationReader populationReader) {
        this.populationReader = populationReader;
    }

    public ArrayList<CovidData> getCovidDatabase() {
        return covidDatabase;
    }

    public ArrayList<PropertyValueData> getPropertyDatabase() {
        return propertyDatabase;
    }

    public ArrayList<PopulationData> getPopulationDatabase() {
        return populationDatabase;
    }

    public void setUpDatabases() throws IOException {

        if (covidReader != null) {
            covidDatabase = (ArrayList<CovidData>) covidReader.returnRecordsList();
        }

        if (populationReader != null) {
            populationDatabase = (ArrayList<PopulationData>) populationReader.returnRecordsList();
        }

        if (propertyReader != null) {
            propertyDatabase = (ArrayList<PropertyValueData>) propertyReader.returnRecordsList();
        }
    }

}

