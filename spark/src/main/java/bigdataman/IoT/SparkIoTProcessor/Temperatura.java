package bigdataman.IoT.SparkIoTProcessor;

import java.io.Serializable;

public class Temperatura implements Serializable{
	private String id;
	private String region;
	private String country;
	private String city;
	private String date;
	private float avg_temperature;

	
	
	public Temperatura() {}

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



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



	public Temperatura(String id, String region, String country, String city, String date,float avg_temperature) {
		this.id = id;
		this.region = region;
		this.country = country;
		this.city = city;
		this.date = date;
		this.avg_temperature = avg_temperature;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}




	
	
}

