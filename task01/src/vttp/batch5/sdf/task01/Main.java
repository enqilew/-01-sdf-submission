package vttp.batch5.sdf.task01;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.crypto.Data;

import vttp.batch5.sdf.task01.models.BikeEntry;
import vttp.batch5.sdf.task01.Utilities;

public class Main {
    static Map<BikeEntry, Integer> cyclistMap = new HashMap<>();
	public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("day.csv"))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                BikeEntry entry = new BikeEntry();
                entry.setSeason(Integer.parseInt(values[0]));
                entry.setMonth(Integer.parseInt(values[1]));
                entry.setHoliday(values[2].equals("1"));
                entry.setWeekday(Integer.parseInt(values[3]));
                entry.setWeather(Integer.parseInt(values[4]));
                entry.setTemperature(Float.parseFloat(values[5]));
                entry.setHumidity(Float.parseFloat(values[6]));
                entry.setWindspeed(Float.parseFloat(values[7]));
                entry.setCasual(Integer.parseInt(values[8]));
                entry.setRegistered(Integer.parseInt(values[9]));

                int totalCyclists = entry.getCasual() + entry.getRegistered();
                cyclistMap.put(entry, totalCyclists);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        
       // Sort entries by total cyclists in descending order using a lambda expression
       List<Map.Entry<BikeEntry, Integer>> topCyclists = cyclistMap.entrySet()
       .stream()
       .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by total cyclists descending
       .limit(5) // Get top 5
       .collect(Collectors.toList());

        // // After getting the top 5 position, loop through    
        //  for (int i = 0; i< topCyclists.length; i ++){
        //      System.out.println("The %d (position) recorded number of cyclists was in  %d (season), on a %d (day) in the month of %d (month).\n There were a total of %d (total) cyclists. The weather was %d (weather).\n %d (day) was %d.", position, season, day, month, total, weather, day, holiday);
        //  }


    }
}
        
//  Read day.csv file and add it into a HashMap
//  Find the total number of cyclists by adding casual and registerd cyclists up
//  After getting the total number of cyclist, sort according to descending order (high to low)
//  This will allow us to get the top 5 days with highest number of cyclists
//  Extract the other values that are in the same row (season, day, month, weather, holiday)
//  Link the values to Utilities.java
//  Print the result out in the a paragraph
       


        
