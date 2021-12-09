package model;

import java.io.Serializable;

public class AvgByYear implements Serializable{

	private String year;
	private float avg_temperature;

	public AvgByYear() {}
	
	public AvgByYear(String year, float avg_temperature) {
		this.year = year;
		this.avg_temperature = avg_temperature;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public float getAvg_temperature() {
		return avg_temperature;
	}

	public void setAvg_temperature(float avg_temperature) {
		this.avg_temperature = avg_temperature;
	}
	
	
	
	
	
	
}
