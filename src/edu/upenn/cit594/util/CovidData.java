package edu.upenn.cit594.util;


public class CovidData {
	
	private int zipCode;
	private String timeStamp;
	private int partiallyVaccinated;
	private int fullyVaccinated;

	
	//constructor
	public CovidData (int zipCode, String timeStamp, int partiallyVaccinated, int fullyVaccinated) {
		this.zipCode = zipCode;
		this.timeStamp = timeStamp;
		this.partiallyVaccinated = partiallyVaccinated;
		this.fullyVaccinated = fullyVaccinated;
	}


	public int getZipCode() {
		return zipCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public int getPartiallyVaccinated() {
		return partiallyVaccinated;
	}

	public int getFullyVaccinated() {
		return fullyVaccinated;
	}
	
}
