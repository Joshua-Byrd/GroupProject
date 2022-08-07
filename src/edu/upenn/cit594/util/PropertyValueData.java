package edu.upenn.cit594.util;

public class PropertyValueData {
	
	//need three fields
	//market_value
	//total_livable_area
	//zip_code
	
	private String marketValue;
	private String totalLivableArea;
	private int zipCode;
	
	public PropertyValueData(String marketValue, String totalLivableArea, int zipCode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipCode = zipCode;
	}


	public String getMarketValue() {
		return marketValue;
	}

	public String getTotalLivableArea() {
		return totalLivableArea;
	}

	public int getZipCode() {
		return zipCode;
	}
}
