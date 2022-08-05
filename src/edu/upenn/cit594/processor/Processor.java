package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.COVIDDatabase;
import edu.upenn.cit594.datamanagement.PopulationDatabase;
import edu.upenn.cit594.datamanagement.PropertyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {

    private COVIDDatabase covidDatabase;
    private PropertyDatabase propertyDatabase;
    private PopulationDatabase populationDatabase;

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

    public List<String> getAvailableDataSet() { return new ArrayList<>();}; //return a list of available datasets

    public int getTotalPopulation(){ return 0;};

    /**
     * Accepts a date in String form, and returns a hashmap where the keys are each of the ZIP codes in the
     * Philadelphia area and the values are the partial vaccinations per capita for that ZIP code
     * @param date to search for vaccination statuses
     * @return Hashmap of ZIP codes/Vaccination status per capita
     */
    public Map<Integer, Double> getPartialVaccinationsPerCapita(String date) { return new HashMap<Integer, Double>();}

    /**
     * Accepts a date in String form and returns a hashmap where the keys are each of the ZIP codes in the
     * Philadelphia area and the values are the full vaccinations per capita for that ZIP code
     * @param date to search vaccination statuses
     * @return HashMap of ZIP codes/vaccination
     */
    public Map<Integer, Double> getFullVaccinationsPerCapita(String date) { return new HashMap<Integer, Double>(); }

    public int getAvgMarketValue(String zipcode) { return 0;};

    public int getAvgLivableValue(String zipcode) { return 0; }

    public int getTotalMarketValue(String zipcode) { return 0; }

    public int getCustomFeature(){ return 0; }

    //custom feature
}