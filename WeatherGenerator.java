
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Calendar;
import java.util.HashMap; 

/**
 * @author Sandeep Rajagopalan
 *	This class contains the main program for the Weather generator application 
 *	The main functions are defined in this class
 */
public class WeatherGenerator implements WeatherGeneratorConst
{
	/**
	 * main method. This method calls the other methods in this class for weather simulation 
	 */
	public static void main (String args[])  
	{
		
		try 
		{
			
			WeatherGenerator objWeatherGenerator = new WeatherGenerator();
			
			// load history data from the file
			HashMap<String, float[]> objHistoryData = objWeatherGenerator.loadHistoryData();
			
			// load location data from the file
			HashMap<String, Location> objLocation = objWeatherGenerator.loadLocation();
			
			// get the cities name
			String[] arrCities = objLocation.keySet().toArray(new String[0]);
			
			// Array to hold the outputs, using the Weather output model
			WeatherOutputModel[] arrOutput = new WeatherOutputModel[arrCities.length];
			
			for (int i = 0; i < arrCities.length; i ++)
			{
				// get a random time
				Calendar objCalendar = objWeatherGenerator.getRandomTime();
				int iMonth = objCalendar.get(Calendar.MONTH);
				int iHour = objCalendar.get(Calendar.HOUR);
				
						// using the keys get the values from the file to variables
						float meanMaxTemp = objHistoryData.get(arrCities[i]+ HASHMAP_DELIMITER + MEAN_MAX_TEMPERATURE )[iMonth];
						float meanMinTemp = objHistoryData.get(arrCities[i]+ HASHMAP_DELIMITER + MEAN_MIN_TEMPERATURE )[iMonth];
						float meanRainfall = objHistoryData.get(arrCities[i]+ HASHMAP_DELIMITER + MEAN_RAINFALL )[iMonth];
						float meanPressure = objHistoryData.get(arrCities[i]+ HASHMAP_DELIMITER + PRESSURE )[iMonth];
						float meanMaxHumidity = objHistoryData.get(arrCities[i]+ HASHMAP_DELIMITER + MEAN_MAX_HUMIDITY )[iMonth];
						float meanMinHumidity = objHistoryData.get(arrCities[i]+ HASHMAP_DELIMITER + MEAN_MIN_HUMIDITY )[iMonth];
						
						Location currentCity = objLocation.get(arrCities[i]);
						
						// get the temperature
						float fCurrentTemp = objWeatherGenerator.tempGenerator(meanMaxTemp, meanMinTemp,  iHour);
						// get the condition
						String strWeatherCondition = objWeatherGenerator.getWeatherCondition(fCurrentTemp, objCalendar, currentCity, meanRainfall );
						// get the pressure
						float fCurrentPressure = objWeatherGenerator.pressureGenerator(meanPressure,meanMaxTemp, meanMinTemp, fCurrentTemp);
						// get the humidity
						int iCurrentHumidity = objWeatherGenerator.humidityGenerator(meanMaxHumidity, meanMinHumidity,meanMaxTemp, meanMinTemp,fCurrentTemp);
						
						arrOutput[i] = new WeatherOutputModel();
						arrOutput[i].setCityName(arrCities[i]);
						arrOutput[i].setPostition(currentCity.getCityLatitude()+ POSITION_DELIMITER 
								+ currentCity.getCityLongitude()+ POSITION_DELIMITER
								+ currentCity.getCityElevation());
						arrOutput[i].setLocalTime(objCalendar);
						arrOutput[i].setCondition(strWeatherCondition);
						arrOutput[i].setTemperature(fCurrentTemp);
						arrOutput[i].setPressure(fCurrentPressure);
						arrOutput[i].setHumidity(iCurrentHumidity);
												
			}

			WeatherFileWriter objWriter = new WeatherFileWriter();
			objWriter.writeFile(OUTPUT_FILE_NAME, arrOutput, OUTPUT_FILE_DELIMITER);
		
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Seed file not found");
			System.out.println(e.getMessage());
		} 
		catch (Exception e) 
		{
			System.out.println("Error. Program stoppend abruptly");
			System.out.println(e.getMessage());
		}
		
	}
	
/**
 * The assumption is that the humidity is inversely proportional to the temperature
 * @param meanMaxHumidity
 * @param meanMinHumidity
 * @param meanMaxTemp
 * @param meanMinTemp
 * @param fCurrentTemp
 * @return fCurrentHumidity - The generated humidity.
 * 
 */
	private int humidityGenerator(float meanMaxHumidity, float meanMinHumidity, float meanMaxTemp, float meanMinTemp,
			float fCurrentTemp) 
	{
		
		// Adjust the current temperature if it is not between min and max temperature.
		// This makes sure that the humidity percentage don't go beyond 100
		if(fCurrentTemp > meanMaxTemp)
			fCurrentTemp = meanMaxTemp;
		else if(fCurrentTemp < meanMinTemp)
			fCurrentTemp = meanMinTemp;
		// the percentage of current temp on the min temp
		float percCurrTemponMinTemp = (fCurrentTemp - meanMinTemp) / (meanMaxTemp - meanMinTemp);
		// total humidity change
		float diffHumidity = meanMaxHumidity - meanMinHumidity;
		// using the percentage calculate the humidity which is inversely proportional to the temperature
		int currentHumidity =  (int)(meanMaxHumidity - (percCurrTemponMinTemp * diffHumidity));
		return currentHumidity;
	}

	/**
	 * Generate the temperature from the mean max temperature, mean min temperature and the hour of the day
	 * This method calculates the hours to the highest temperature from lowest temperature and the reverse.
	 * Assumption is made that the temperature changes gradually.
	 * Finally a random value is added to the temperature.
	 * @param meanMaxTemp
	 * @param meanMinTemp
	 * @param iHour
	 * @return temperature in  float
	 */
	private float tempGenerator(float meanMaxTemp,float meanMinTemp, int iHour) 
	{
		
		// flat to mark whether the low temperature before the midnight
		boolean isLowBeforeMidnight = false;
		float fCurrentTemp = 0f;
		float diffTemp = meanMaxTemp - meanMinTemp;
		
		// time to peak temperature from the low temperature
		int hoursToPeak = HIGH_TEMP_TIME_OF_DAY - LOW_TEMP_TIME_OF_DAY;
		
		// if the low temp time is before midnight, above will be a negative value
		// adjust the hours by adding 24
		if (hoursToPeak < 0)
			hoursToPeak = 24 + hoursToPeak;
			
		// time to low temperature from highest temp
		int hoursToLow = LOW_TEMP_TIME_OF_DAY - HIGH_TEMP_TIME_OF_DAY;
		if(hoursToLow < 0)
		{
			hoursToLow = 24 + hoursToLow;
		}
		else
		{
			isLowBeforeMidnight = true;
		}
			
			
		// calculate the change per hour for going up and going down in temperatures
		float changePerHourToPeak = (float)diffTemp / hoursToPeak;
		float changePerHourToLow = (float)diffTemp / hoursToLow;
					
		if (iHour < HIGH_TEMP_TIME_OF_DAY )
		{
			if(iHour > LOW_TEMP_TIME_OF_DAY || isLowBeforeMidnight) // going to high
			{
				int iDiffHour = HIGH_TEMP_TIME_OF_DAY - iHour;
				fCurrentTemp = meanMaxTemp - (iDiffHour * changePerHourToPeak);
			}
			else// going to low
			{
				int iDiffHour = LOW_TEMP_TIME_OF_DAY - iHour;
				fCurrentTemp = meanMinTemp + (iDiffHour * changePerHourToLow);
			}
				
		}
		else 
		{
			if(isLowBeforeMidnight && iHour > LOW_TEMP_TIME_OF_DAY) // going to high
			{
				int iDiffHour = iHour - LOW_TEMP_TIME_OF_DAY;
				fCurrentTemp = meanMinTemp + (iDiffHour * changePerHourToPeak);
			}
			else // going to low
			{
				int iDiffHour = iHour - HIGH_TEMP_TIME_OF_DAY;
				fCurrentTemp = meanMaxTemp - (iDiffHour * changePerHourToLow);
			}
			
		}
		
		// add a random 
		// The below code adds a random integer between -3 to 5
		fCurrentTemp = fCurrentTemp + new Random().nextInt(RANDOM_FACTOR * 2 ) - RANDOM_FACTOR;
		
		// round for 1 decimal place
		return (float)Math.round(fCurrentTemp * 10)/10 ;
	}
	
	/***
	 * This method reads the weather statistics file and stores it in a Hashmap
	 * The key for hashmap is the city name appended with the metrics value
	 * @return HashMap containing the city name and metric name as the key and values are the metric array
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
		private HashMap<String, float[]> loadHistoryData() throws FileNotFoundException, Exception
		{
			// use the File reader object read the csv file 	
			WeatherFileReader objFileReader = new WeatherFileReader(WEATHER_STAT_FILE,WEATHER_STAT_DELIMITER);
			String[][] arrStrValues = objFileReader.readFile(true);
			// Hashmap to hold the results
			HashMap<String,float[]> mapMeasureMonthsData = new HashMap<String, float[]>();
			// parse through the lines
			for(int i=0; i < arrStrValues.length; i++)
			{
				// we are expecting 14 columns in the file. Through an Exception if it is not 14 columns
				if(arrStrValues[i].length!= WEATHER_STAT_COLUMN_NUMBERS)
					throw new Exception("Unexpected array length for the history data");
				// the first two columns represent the city name and metric name. 
				// the length for the values array will be total columns - 2
				float[] arrMeasureMonthsData = new float[arrStrValues[i].length - 2];
				// parse through the columns
				for(int j = 2; j < arrStrValues[i].length; j++)
				{
					arrMeasureMonthsData[j-2] = Float.parseFloat(arrStrValues[i][j]);  
				}
					
				mapMeasureMonthsData.put(arrStrValues[i][1]+ HASHMAP_DELIMITER + arrStrValues[i][0], arrMeasureMonthsData);
						
				}
							
			return  mapMeasureMonthsData;
	  	}
		
		/**
		 * This method reads the city location details from the csv file
		 * @return HashMap containing the the city name as the key and value of the location object
		 * @throws FileNotFoundException
		 * @throws Exception
		 */
		private HashMap<String, Location> loadLocation()throws FileNotFoundException, Exception
		{
			// HashMap to return	
			HashMap<String, Location> objCityMap = new HashMap<String,Location>();
			// Read the file using File reader
			WeatherFileReader objFileReader = new WeatherFileReader(LOCATION_FILE,LOCATION_FILE_DELIMITER);
			String[][] arrStrValues = objFileReader.readFile(true);
			
			for(int i=0; i < arrStrValues.length; i++)
			{
				// Expecting 5 columns in the file. If there are difference in the column numbers throw error
				if(arrStrValues[i].length!= LOCATION_FILE_COLUMN_NUMBERS)
					throw new Exception("Unexpected array length for the location data");
				Location objLocation = new Location();
				objLocation.setCanHaveSnow(Boolean.parseBoolean(arrStrValues[i][1]));
				objLocation.setCityLatitude(arrStrValues[i][2]);
				objLocation.setCityLongitude(arrStrValues[i][3]);
				objLocation.setCityElevation(arrStrValues[i][4]); 
				objCityMap.put(arrStrValues[i][0], objLocation);
				
			}
				
			return objCityMap;
			
		}
		
		
		/**
		 * This method returns a Calendar object with random time
		 * @return Calendar object with random time
		 */
		private Calendar getRandomTime()
		{
			Calendar objCalendar = Calendar.getInstance();
			Random objRandom = new Random();
			
			
			int year = CALENDAR_YEAR_CONST + Math.abs(objRandom.nextInt(100));
			int month = objRandom.nextInt()% CALENDAR_MONTH_CONST;
			int date = objRandom.nextInt()% CALENDAR_DAYS_CONST;
			int hourOfDay = objRandom.nextInt()% CALENDAR_HOURS_CONST;
			int minute = objRandom.nextInt()% CALENDAR_MINUTES_CONST;
			
			objCalendar.set(year, month, date, hourOfDay, minute);
			
			return objCalendar;
		}
		
		/**
		 * 
		 * @param temp current temperature
		 * @param time current time
		 * @param city city name
		 * @param meanRainfall mean value of the rain fall
		 * @return Weather condition - Sunny, Rainy or Snow
		 */
		private String getWeatherCondition(float temp, Calendar time, Location city, float meanRainfall)
		{
			
			Random objRandom = new Random();
			String strCondition = CONDITION_SUNNY;
			
			
			// if the city has the probability of snow
			if(city.isCanHaveSnow() && temp < SNOW_TEMP){
				strCondition = CONDITION_SNOW;
			}
			// if it is not snowing and night, then assume it is raining.
			// the reason for this assumption is as per requirement only three conditions are present.
			// They are Sunny, Snow and Rainy. It does not make sense to show Sunny for night hours
			else if(time.get(Calendar.HOUR_OF_DAY) < SUNRISE_HOUR || time.get(Calendar.HOUR_OF_DAY) > SUNSET_HOUR)
			{
				strCondition = CONDITION_RAIN;
			}
			// day time and not snowing.
			// still there is a possibility of rain. 
			// decide the rain depending on the probability 
			else
			{
				int iProb = (int) (RAIN_PROBABILITY_FACTOR - meanRainfall);
				if(iProb <= 0)
					iProb = 1;
				if(objRandom.nextInt(iProb)==0) // depends on the probability, if the random value is zero assume it is raining
				{
					strCondition = CONDITION_RAIN;
				}
			}
			
			return strCondition;
			
		}

		/**
		 *  This method generate the pressure
		 *  Pressure is randomly generated with a random value added to the mean pressure 
		 *  A positive or negative shift is added depending on the temperature
		 * @param meanPressure
		 * @return pressure in hPa
		 */
		
		private float pressureGenerator(float meanPressure, float meanMaxTemp, float meanMinTemp,
				float fCurrentTemp)
		{
			
			Random objRandom = new Random();
			int multiplier = 1;
			// Adjust the current temperature if it is not between min and max temperature.
			if(fCurrentTemp > meanMaxTemp)
				fCurrentTemp = meanMaxTemp;
			else if(fCurrentTemp < meanMinTemp)
				fCurrentTemp = meanMinTemp;
			// the percentage of current temp on the min temp
			float percCurrTemponMinTemp = (fCurrentTemp - meanMinTemp) / (meanMaxTemp - meanMinTemp);
			
			
			// if the temperature is more than the average value, add a negative shift for pressure
			if(percCurrTemponMinTemp > 0.5)
				multiplier = -1;
			
			float fShift = PRESSURE_RANDOM_MULTIPLY_FACTOR * objRandom.nextFloat() * multiplier;
			// add the shift to the mean value and convert to one decimal place
			float currentPressure = (float)Math.round((meanPressure + fShift) * 10)/10;
			
			return currentPressure ;
			
		}
	
	}
	


