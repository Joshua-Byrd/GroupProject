package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.PropertyValueData;

import java.util.List;

/**
 *
 */
public class MktValSum implements FieldSum{
    @Override
    public double getSum(int zipCode, List<PropertyValueData> propertyData) {
        double sum = 0;
        for (PropertyValueData p: propertyData) {
            if(p.getZipCode() == zipCode) {
                try{
                    sum += Double.parseDouble(p.getMarketValue());
                } catch (Exception e){}
            }
        }

        return sum;
    }
}
