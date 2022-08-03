package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.COVIDDatabase;
import edu.upenn.cit594.datamanagement.PopulationDatabase;
import edu.upenn.cit594.datamanagement.PropertyDatabase;

import java.util.ArrayList;
import java.util.List;
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

    public double getPartialVaccinationsPerCapita() { return 0;}

    public double getFullVaccinationsPerCapita() { return 0; }

    public double getAvgMarketValue() { return 0.0;};

    public double getAvgLivableValue() { return 0; }

    public double getTotalMarketValue() { return 0; }

    //custom feature
}