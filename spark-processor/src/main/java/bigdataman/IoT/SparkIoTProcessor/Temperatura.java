package bigdataman.IoT.SparkIoTProcessor;

import java.io.Serializable;

public class Temperatura implements Serializable{
	private String region;
	private String country;
	private String city;
	private String timestamp;
	private String year;
	private float avg_temperature;

	
	
	public Temperatura() {}



	public String getRegion() {
		return region;
	}



	public void setRegion(String region) {
		this.region = region;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
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



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public Temperatura(String region, String country, String city, String timestamp, String year, float avg_temperature) {
		this.region = region;
		this.country = country;
		this.city = city;
		this.timestamp = timestamp;
		this.year= year;
		this.avg_temperature = avg_temperature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}




	
	
}

