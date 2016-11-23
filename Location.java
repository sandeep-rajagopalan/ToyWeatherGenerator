package com.sandeep.rajagopalan.weathersimulator;

/**
 * @author Sandeep Rajagopalan
 *	This class contains the Location details of the cities
 *	
 */

public class Location { 
	
	private String cityLatitude;
	private String cityLongitude;
	private String cityElevation;
	private boolean canHaveSnow;

	
	
	
	/**
	 * @return the cityLatitude
	 */
	public String getCityLatitude() {
		return cityLatitude;
	}
	/**
	 * @param cityLatitude the cityLatitude to set
	 */
	public void setCityLatitude(String cityLatitude) {
		this.cityLatitude = cityLatitude;
	}
	/**
	 * @return the cityLongitude
	 */
	public String getCityLongitude() {
		return cityLongitude;
	}
	/**
	 * @param cityLongitude the cityLongitude to set
	 */
	public void setCityLongitude(String cityLongitude) {
		this.cityLongitude = cityLongitude;
	}
	/**
	 * @return the cityElevation
	 */
	public String getCityElevation() {
		return cityElevation;
	}
	/**
	 * @param cityElevation the cityElevation to set
	 */
	public void setCityElevation(String cityElevation) {
		this.cityElevation = cityElevation;
	}
	/**
	 * @return the canHaveSnow
	 */
	public boolean isCanHaveSnow() {
		return canHaveSnow;
	}
	/**
	 * @param canHaveSnow the canHaveSnow to set
	 */
	public void setCanHaveSnow(boolean canHaveSnow) {
		this.canHaveSnow = canHaveSnow;
	}


}
