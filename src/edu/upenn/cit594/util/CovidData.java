package edu.upenn.cit594.util;


public class CovidData {
	
	private int zipCode;
	private String timeStamp;
	private String partiallyVaccinated;
	private String fullyVaccinated;
	private String deaths;

	
	//constructor
	public CovidData (int zipCode, String timeStamp, String partiallyVaccinated, String fullyVaccinated, String deaths) {
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

	public String getPartiallyVaccinated() {
		return partiallyVaccinated;
	}

	public String getFullyVaccinated() {
		return fullyVaccinated;
	}
	public String getDeaths() { return deaths; }

	public String toString(){
		return "Timestamp: " + timeStamp + ", Zip: " + zipCode + ", Deaths: " + deaths +
				", Fully Vaxxed: " + fullyVaccinated + ", Partially Vaxxed: " + partiallyVaccinated;
	}

}
