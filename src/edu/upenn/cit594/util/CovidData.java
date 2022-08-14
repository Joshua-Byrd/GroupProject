package edu.upenn.cit594.util;


public class CovidData {
	
	private int zipCode;
	private String timeStamp;
	private int partiallyVaccinated;
	private int fullyVaccinated;

	private int deaths;

	
	//constructor
	public CovidData (int zipCode, String timeStamp, int partiallyVaccinated, int fullyVaccinated, int deaths) {
		this.zipCode = zipCode;
		this.timeStamp = timeStamp;
		this.partiallyVaccinated = partiallyVaccinated;
		this.fullyVaccinated = fullyVaccinated;
		this.deaths = deaths;
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
	public int getDeaths() { return deaths; }

	public String toString(){
		return "Timestamp: " + timeStamp + ", Zip: " + zipCode + ", Deaths: " + deaths +
				", Fully Vaxxed: " + fullyVaccinated + ", Partially Vaxxed: " + partiallyVaccinated;
	}

}
