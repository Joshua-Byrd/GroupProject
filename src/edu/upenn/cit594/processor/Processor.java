package edu.upenn.cit594.processor;
import edu.upenn.cit594.datamanagement.COVIDReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.PopulationData;
import edu.upenn.cit594.util.PropertyValueData;

import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.*;

public class Processor {

    COVIDReader covidReader;
    PropertyReader propertyReader;
    PopulationReader populationReader;
    
    private static final DecimalFormat df = new DecimalFormat("0.0000");


    /*-----Data lists-----*/

    private ArrayList<CovidData> covidDatabase;
    private ArrayList<PropertyValueData> propertyDatabase;
    private ArrayList<PopulationData> populationDatabase;

    /*-----Memoization variables-----*/

    private int totalPopulation = 0;
    Map<Integer, Double> partialVaccinationResults = new TreeMap<>();
    Map<Integer, Double> fullVaccinationResults = new TreeMap<>();
    Map<Integer, Integer> avgMktValueResults = new HashMap<>();
    Map<Integer, Integer> avgTotLivAreaResults = new HashMap<>();
    Map<Integer, Integer> totMktValuePerCapitaResults = new HashMap<>();
    Map<Integer, Integer> customFeatureResults = new HashMap<>();

    public Processor(Reader...arr) {

    }


    /*-----Data Calculation Methods-----*/

    /**
     * Returns a list of the datasets that are currently in the program
     *
     * @return
     */
    public List<String> getAvailableDataSet() {
    	
    	List availableDataSet = new ArrayList<>();
    	
    	try { 
    		if (covidDatabase.size()>=0) {availableDataSet.add("covid");}
    	} catch (Exception e) {
    		; // do nothing and not add to the list
    	}
    	   	
    	try {
    		if (propertyDatabase.size()>=0) {availableDataSet.add("property");}
    	} catch (Exception e) {
    		; // do nothing and not add to the list
    	}
    	
    	try {
    		if (populationDatabase.size()>=0) {availableDataSet.add("population");}
    	} catch (Exception e) {
    		; // do nothing and not add to the list
    	}
    	
    	availableDataSet.sort(null); //sorting using natural string order
    	
    	return availableDataSet;
    }

    /**
     * Memoization method. Calculates the total population only if totalPopulation
     * has not been set. Otherwise, returns totalPopulation.
     *
     * @return totalPopulation
     */
    public int getTotalPopulation() {
        if (totalPopulation == 0) {
        	
        	for (PopulationData p: populationDatabase) {
        		totalPopulation = p.getPopulation() + totalPopulation;
        	}        	
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
        	
        	for (CovidData pvax: covidDatabase) {
        		if (pvax.getTimeStamp().contains(date)) {
        			for (PopulationData p: populationDatabase) {
        				if (p.getZipCode() == pvax.getZipCode() && p.getPopulation() != 0) {
        					double perCapita;
        					
        					perCapita = (double) pvax.getPartiallyVaccinated()/p.getPopulation();	
        					
        	        		partialVaccinationResults.put(pvax.getZipCode(), Double.parseDouble(df.format(perCapita))); 
        	        		//losing the last 2 digits if they are 0. 
        	        		//Need to figure that out
        				}
        				
        			}        			        			
        		}
        	
        }

        return partialVaccinationResults;
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

        	for (CovidData pvax: covidDatabase) {
        		if (pvax.getTimeStamp().contains(date)) {
        			for (PopulationData p: populationDatabase) {
        				if (p.getZipCode() == pvax.getZipCode()) {
        					double perCapita;
        					
        					perCapita = (double) pvax.getFullyVaccinated()/p.getPopulation();	
        					
        					fullVaccinationResults.put(pvax.getZipCode(), Double.parseDouble(df.format(perCapita))); 
        	        		//losing the last 2 digits if they are 0. 
        	        		//Need to figure that out
        				}
        				
        			}        			        			
        		}
        	
        }

        return fullVaccinationResults;
    }
		return fullVaccinationResults;
   }
  


    public int calculateAverages(String field, int zipCode) {
        int sum = 0;
        int count = 0;


        //calculate for average market value
        if ("avgMktValue".equals(field)) {
            //calculate results only if not present in the results map
            if(!avgMktValueResults.containsKey(zipCode)) {

                //sum all market values and count up all properties in that zip code
                for (PropertyValueData record : propertyDatabase) {
                    if (record.getZipCode() == zipCode) {
                        count++;
                        sum += record.getMarketValue();
                    }
                }
                //add result to results map
                avgMktValueResults.put(zipCode, sum / count);
                return sum / count;
            } else { return avgMktValueResults.get(zipCode);}
        } else { // calculate for total liveable area

            //calculate results only if not present in the results map
            if(!avgTotLivAreaResults.containsKey(zipCode)) {

                //sum all total liveable values and count up all properties in that zip code
                for (PropertyValueData record : propertyDatabase) {
                    if (record.getZipCode() == zipCode) {
                        count++;
                        sum += record.getTotalLivableArea();
                    }
                }
                //add result to results map
                avgMktValueResults.put(zipCode, sum / count);
                return sum/count;
            } else { return avgTotLivAreaResults.get(zipCode);}
        }
    }


    public int getAvgMarketValue(int zipCode) {
         return calculateAverages("avgMktValue", zipCode);
    }


    public int getAvgTotLivableArea(int zipCode) {
       return calculateAverages("totLiveableArea", zipCode);
    }

    public int getTotalMarketValue(int zipcode) {
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

//        System.out.println("covid records: " + covidDatabase.size());
//        System.out.println("population records: " + populationDatabase.size());
//        System.out.println("properties records: " + propertyDatabase.size());
    }

}

