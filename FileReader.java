package com.sandeep.rajagopalan.weathersimulator;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author sandeep_mr
 * This class is used for reading the seed file for the Weather generator app
 *
 */
public class FileReader  
{

	String delimiter;
	String fileName;
	Scanner objScanner;
	
	
	/***
	 * Constructor
	 * @param fileName the file to be read
	 * @param delimiter delimiter of the file
	 * @throws FileNotFoundException 
	 * 
	 */
	public FileReader(String fileName, String delimiter) throws FileNotFoundException 
	{
		this.fileName = fileName;
		this.delimiter = delimiter;
		objScanner = new Scanner(new File(fileName));
		objScanner.useDelimiter(delimiter);
				
	}
	
	/**
	 * @return the delimiter
	 */
	public String getDelimiter() 
	{
		return delimiter;
	}

	/**
	 * @param delimiter the delimiter to set
	 */
	public void setDelimiter(String delimiter) 
	{
		this.delimiter = delimiter;
	}

	/**
	 * @return whether the String has more elements
	 */
	public boolean hasMoreElements()
	{
		return objScanner.hasNext();
	}
	
	
	/**
	 * @return whether the file has more lines
	 */
	public boolean hasNextLine()
	{
		return objScanner.hasNextLine();
	}
	
	/**
	 * @return return the next line String
	 */
	public String getNextLine()
	{
		return objScanner.nextLine();
	}
	
	/**
	 * @return the element
	 */
	public String getNextElement()
	{
			return(objScanner.next());
	}
	
	/**
	 * @param hasHeader whether the file has the header line
	 * @return two dimensional String array with the elements
	 * First dimension is the rows and second dimension is the columns
	 * @throws FileNotFoundException
	 */
	public String[][] readFile(boolean hasHeader) throws FileNotFoundException
	{
		ArrayList<String[]> objArrayList = new ArrayList<String[]>();
		if(hasHeader && objScanner.hasNextLine())
			// ignore the header line
			objScanner.nextLine();
		while(objScanner.hasNextLine())
		{
			String strLine = objScanner.nextLine();
			String[] arrStrValues = strLine.split(delimiter);
			objArrayList.add(arrStrValues);
		}	
		return objArrayList.toArray(new String[objArrayList.size()][]);
	}
	
}
