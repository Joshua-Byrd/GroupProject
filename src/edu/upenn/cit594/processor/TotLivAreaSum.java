package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.PropertyValueData;

import java.util.List;
import java.util.Map;

/**
 * Returns the sum of the total liveable area field  for a given zip code in the properties database
 */
public class TotLivAreaSum implements FieldSum{

    @Override
    public double getSum(int zipCode, List<PropertyValueData> propertyData) {
        double sum = 0;
        for (PropertyValueData p: propertyData) {
            if(p.getZipCode() == zipCode) {
                try{
                    sum += Double.parseDouble(p.getTotalLivableArea());
                } catch (Exception e){
                    Processor.counter++;
                }
            }
        }

        return sum;
    }
}
