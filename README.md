# ToyWeatherGenerator

This Toy weather generator is written in Java. Seed files are used to get the historical values of weather and the city location values.
The WeatherStat file contains the average monthly values of temperature, humidity, rainfall and atomospheric pressure is used. 

Given below is a high level logic and assumptions
-------------------------------------------------

Time : A random time from 2000 till 2100

Temperature calculation : Used the mean average min value and mean average max value. Assumption is made that the temperature change gradually from the min value to max value. Constants values in interface are used, for maintenability. A random shift also added to the final arrived value of temperature.

Weather condition : condition is arrived as the function of temperature, time and mean rainfall. A probability factor is used depending on the mean rainfall. Ie; if mean rainfall is high, the probaility of rain also high in the city. The conditions expected from the problem statement is Snow, Rain and Sunny. Due to this an assumption is made to show it is raining in the night if the condition is not snow.

Pressure : An assumption is made that if the temperature raises the pressure goes down. A random shift is added to the mean value of the pressure.

Humidity : An assumption is made that the humidity is inversely proportional to the temperature.



Java Classes/Interfaces written
--------------------------------

1. WeatherGenerator : This class contains the main method to run. Most of the logic of the toy weather generator is written in this class
2. WeatherGeneratorConst : This is an interface contains all the constants used in the program
3. WeatherFileReader : This class is used to read the seed file
4. WeatherFileWriter : This class is used for writing to the output file with the delimiter
5. WeatherOutputModel : This is the output model which is used to format the output as the way required
6. Location : This is a class to hold the city details

Running the program
-------------------
1. Below seed files need to be kept in the folder ./Seed
    a. WeatherStat.csv
    b. Cities.csv
2. Compile all the java files using javac
3. Run the main program using java WeatherGenerator
4. The output file out.txt will be generated in the root folder




