package com.sandeep.rajagopalan.weathersimulator;

import java.util.Calendar; 

/**
 * @author sandeep_mr
 * This class acts as a model for the Weather output
 */
public class WeatherOutputModel  
{

	private String cityName;
	private String postition;
	private Calendar localTime;
	private String condition;
	private float temperature;
	private float pressure;
	private int humidity;
	
	/**
	 * @return the cityName
	 */
	public String getCityName() 
	{
		return cityName;
	}
	
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) 
	{
		this.cityName = cityName;
	}
	
	/**
	 * @return the position
	 */
	public String getPostition() 
	{
		return postition;
	}
	
	/**
	 * @param postition the position to set
	 */
	public void setPostition(String postition) 
	{
		this.postition = postition;
	}

	/**
	 * @return the localTime
	 */
	public Calendar getLocalTime() 
	{
		return localTime;
	}
	
	/**
	 * @return the time in the format as required for the output
	 */
	public String getTimeFormatted()
	{
		int year = localTime.get(Calendar.YEAR);
		int month = localTime.get(Calendar.MONTH);
		int day = localTime.get(Calendar.DATE);
		int hour = localTime.get(Calendar.HOUR);
		int minute = localTime.get(Calendar.MINUTE);
		int second = localTime.get(Calendar.SECOND);
		return String.valueOf(year)+ "-"
				+getFormattedStringForInts(month) + "-"
				+getFormattedStringForInts(day) + "T"
				+getFormattedStringForInts(hour) + ":"
				+getFormattedStringForInts(minute) + ":"
				+getFormattedStringForInts(second) + "Z";
	}
	
	/**
	 * @param localTime the localTime to set
	 */
	public void setLocalTime(Calendar localTime) 
	{
		this.localTime = localTime;
	}
	
	/**
	 * @return the condition
	 */
	public String getCondition() 
	{
		return condition;
	}
	
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) 
	{
		this.condition = condition;
	}
	
	/**
	 * @return the temperature with the sign
	 */
	public String getTemperature() 
	{
		String strTemp=String.valueOf(temperature);
		if(temperature > 0)
			strTemp = "+" + temperature;
		
		return strTemp;
	}
	
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(float temperature) 
	{
		this.temperature = temperature;
	}
	
	/**
	 * @return the pressure
	 */
	public float getPressure() 
	{
		return pressure;
	}
	
	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(float pressure) 
	{
		this.pressure = pressure;
	}
	
	/**
	 * @return the humidity
	 */
	public int getHumidity() 
	{
		return humidity;
	}
	
	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(int humidity) 
	{
		this.humidity = humidity;
	}
	
	/**
	 * @param iValue integer value to be formatted
	 * @return return with a prefix of '0' if the value is single digit
	 */
	private static String  getFormattedStringForInts(int iValue)
	{
		String strReturnValue = String.valueOf(iValue);
		if(iValue < 10){
			strReturnValue = "0" + strReturnValue;
		}
		return strReturnValue ;
	}
	
	
}
