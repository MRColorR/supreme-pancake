package model;

import java.io.Serializable;

public class AvgByCity implements Serializable {
	private String city;
	private float avg_temperature;
	
	public AvgByCity() {}

	public AvgByCity(String city, float avg_temperature) {
		this.city = city;
		this.avg_temperature = avg_temperature;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public float getAvg_temperature() {
		return avg_temperature;
	}

	public void setAvg_temperature(float avg_temperature) {
		this.avg_temperature = avg_temperature;
	}
	
	
}
