package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.PropertyValueData;

import java.util.List;
import java.util.Map;

/**
 * Interface for strategy pattern utilized by the Average Market Value and
 * Average Total Liveable area operations
 */
public interface FieldSum {
    public double getSum(int zipCode, List<PropertyValueData> propertyData);
}
