package model;

import java.io.Serializable;

public class AvgTempByRegionYear implements Serializable {
	private String year;
	private String region;
	private float avg_temperature;
	
	public AvgTempByRegionYear() {}

	public AvgTempByRegionYear(String year, String region, float avg_temperature) {
		this.year = year;
		this.region = region;
		this.avg_temperature = avg_temperature;
	}
	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public float getAvg_temperature() {
		return avg_temperature;
	}

	public void setAvg_temperature(float avg_temperature) {
		this.avg_temperature = avg_temperature;
	}
	
}
