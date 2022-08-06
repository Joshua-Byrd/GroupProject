package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.COVIDDatabase;
import edu.upenn.cit594.datamanagement.PopulationDatabase;
import edu.upenn.cit594.datamanagement.PropertyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {

    /*-----Databases-----*/

    private COVIDDatabase covidDatabase;
    private PropertyDatabase propertyDatabase;
    private PopulationDatabase populationDatabase;

    /*-----Memoization variables-----*/

    private int totalPopulation = 0;
    Map<Integer, Double> partialVaccinationResults = new HashMap<>();
    Map<Integer, Double> fullVaccinationResults = new HashMap<>();
    Map<String, Integer> avgMktValueResults = new HashMap<>();
    Map<String, Integer> avgTotLivAreaResults = new HashMap<>();
    Map<String, Integer> totMktValuePerCapitaResults = new HashMap<>();
    Map<String, Integer> customFeatureResults = new HashMap<>();

    public void processor(){
        //how do we set the databases
    }


    /*-----Data Calculation Methods-----*/

    /**
     * Returns a list of the datasets that are currently in the program
     * @return
     */
    public List<String> getAvailableDataSet() { return new ArrayList<>();}; //return a list of available datasets

    /**
     * Wrapper method for the calculateTotalPopulation method
     * @return
     */
    public int getTotalPopulation(){ return calculateTotalPopulation(); };

    /**
     * Memoization method. Calculates the total population only if totalPopulation
     * has not been set. Otherwise, returns totalPopulation.
     * @return totalPopulation
     */
    public int calculateTotalPopulation() {
        if (totalPopulation == 0) {
            //perform calculations with dataset
            //and set totalPopulation
        }
        return totalPopulation;
    }

    /**
     * Accepts a date in String form, and returns a hashmap where the keys are each of the ZIP codes in the
     * Philadelphia area and the values are the partial vaccinations per capita for that ZIP code
     * @param date to search for vaccination statuses
     * @return Hashmap of ZIP codes/Vaccination status per capita
     */
    public Map<Integer, Double> getPartialVaccinationsPerCapita(String date) { return new HashMap<Integer, Double>();}

    public Map<Integer, Double> calcPartialVaccinatoinsPerCapita(String date) {
        if (partialVaccinationResults.size() == 0) {
            //perform calculations with dataset
            //and set partialVaccinationResults
        }

        return partialVaccinationResults;
    }

    /**
     * Accepts a date in String form and returns a hashmap where the keys are each of the ZIP codes in the
     * Philadelphia area and the values are the full vaccinations per capita for that ZIP code
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
    };

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
        if(totMktValuePerCapitaResults.containsKey(zipcode)) {
            return totMktValuePerCapitaResults.get(zipcode);
        } else {
            //calculate total market value per capita
            //and add to totMktValuePerCapitaResults
            //return result
            return 0;
        }
    }

    public int getCustomFeature(String input){
        if (customFeatureResults.containsKey(input)){
            return customFeatureResults.get(input);
        } else {
            //calculate the custom feature and add
            //to customFeatureResults
            //return result
            return 0;
        }
    }


    /*-----Getters and Setters-----*/
    public COVIDDatabase getCovidDatabase() {
        return covidDatabase;
    }

    public PropertyDatabase getPropertyDatabase() {
        return propertyDatabase;
    }

    public PopulationDatabase getPopulationDatabase() {
        return populationDatabase;
    }

    public void setCovidDatabase(COVIDDatabase covidDatabase) {
        this.covidDatabase = covidDatabase;
    }

    public void setPropertyDatabase(PropertyDatabase propertyDatabase) {
        this.propertyDatabase = propertyDatabase;
    }

    public void setPopulationDatabase(PopulationDatabase populationDatabase) {
        this.populationDatabase = populationDatabase;
    }
}