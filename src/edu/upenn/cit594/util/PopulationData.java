package edu.upenn.cit594.util;

public class PopulationData {
	
	private int zipCode;
	private int population;
	
	public PopulationData (int zipCode, int population) {
		this.zipCode = zipCode;
		this.population = population;
	}

	public int getZipCode() {
		return zipCode;
	}

	public int getPopulation() {
		return population;
	}

}
