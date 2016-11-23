
package com.sandeep.rajagopalan.weathersimulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author sandeep_mr
 * This class is used to write to the output file 
 *
 */
public class WeatherFileWriter 
{
	
	/**
	 * @param filePath
	 * @param arrOutput output values in the WeatherOutputModel array
	 * @param delimiter delimiter for the output file
	 * @throws IOException
	 */
	public void writeFile(String filePath, WeatherOutputModel[] arrOutput, String delimiter) throws IOException
	{
		File file = new File(filePath);

		// if file doesn't exists, then create it
		if (!file.exists())
		{
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i=0; i < arrOutput.length; i ++)
		{
			bw.write(arrOutput[i].getCityName()+ delimiter);
			bw.write(arrOutput[i].getPostition()+ delimiter);
			bw.write(arrOutput[i].getTimeFormatted()+ delimiter);
			bw.write(arrOutput[i].getCondition()+ delimiter);
			bw.write(arrOutput[i].getTemperature()+ delimiter);
			bw.write(arrOutput[i].getPressure()+ delimiter);
			bw.write(String.valueOf(arrOutput[i].getHumidity()));
			bw.newLine();
		}
		
		bw.close();

	}

}
