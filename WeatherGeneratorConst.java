/**
 * 
 */
package com.sandeep.rajagopalan.weathersimulator;

/**
 * @author sandeep_mr
 * This interface contains the constants used for the Weather Generator application
 *
 */
public interface WeatherGeneratorConst { 
	public String MEAN_MAX_HUMIDITY              = "Maximum Humidity";
	public String MEAN_MIN_HUMIDITY              = "Minimum Humidity";
	public String MEAN_MAX_TEMPERATURE  = "Mean Max Temperature";
	public String MEAN_MIN_TEMPERATURE  = "Mean Min Temperature";
	public String MEAN_RAINFALL         = "Mean Rainfall";
	public String PRESSURE              = "Pressure";
	public String HASHMAP_DELIMITER      = "|";
	public int HIGH_TEMP_TIME_OF_DAY    = 14;
	public int LOW_TEMP_TIME_OF_DAY    = 1;
	public int CALENDAR_YEAR_CONST = 2000;
	public int CALENDAR_MONTH_CONST = 12;
	public int CALENDAR_DAYS_CONST = 12;
	public int CALENDAR_HOURS_CONST = 24;
	public int CALENDAR_MINUTES_CONST = 60;
	public int RANDOM_FACTOR = 3;
	// An integer used to come up with the probability of rain
	// if the mean rain fall for a city >= RAIN_PROBABILITY_FACTOR then the probaility is 1. 
	// ie; it will be raining each day depends on the temperature
	// if the mean rain fall is low the probability also goes down 
	public int RAIN_PROBABILITY_FACTOR = 100;
	public int SNOW_TEMP = 0;
	public String CONDITION_SNOW = "Snow";
	public String CONDITION_RAIN = "Rain";
	public String CONDITION_SUNNY = "Sunny";
	public int SUNRISE_HOUR = 6;
	public int SUNSET_HOUR = 19;
	public int PRESSURE_RANDOM_MULTIPLY_FACTOR = 10;
	public String OUTPUT_FILE_NAME = "out.txt";
	public String OUTPUT_FILE_DELIMITER = "|";
	public String POSITION_DELIMITER = ",";
	public String WEATHER_STAT_FILE = "Seed\\WeatherStat.csv";
	public String WEATHER_STAT_DELIMITER = ",";
	public int WEATHER_STAT_COLUMN_NUMBERS = 14;
	public String LOCATION_FILE = "Seed\\Cities.csv";
	public String LOCATION_FILE_DELIMITER = ",";
	public int LOCATION_FILE_COLUMN_NUMBERS = 5;

}
