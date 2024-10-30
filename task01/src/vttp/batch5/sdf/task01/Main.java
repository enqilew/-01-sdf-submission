package vttp.batch5.sdf.task01;

import vttp.batch5.sdf.task01.models.BikeEntry;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String fileName = "day.csv"; 
        List<BikeEntry> entries = new ArrayList<>();

        // Load data from day.csv into BikeEntry instances
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));

            // Check if file has at least one line for headers and skip it
            boolean hasHeader = lines.get(0).contains("season"); // Assuming "season" is in the header
            int startIndex = hasHeader ? 1 : 0;

            // Process each line (excluding the header if present)
            for (int i = startIndex; i < lines.size(); i++) {
                String[] cols = lines.get(i).split(",");
                entries.add(BikeEntry.toBikeEntry(cols));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Sort by total cyclists (casual + registered) in descending order
        entries.sort((a, b) -> Integer.compare(b.getCasual() + b.getRegistered(), a.getCasual() + a.getRegistered()));

        // Define output positions
        String[] positions = { "highest", "second highest", "third highest", "fourth highest", "fifth highest" };

        // Print top 5 days
        for (int i = 0; i < 5 && i < entries.size(); i++) {
            BikeEntry entry = entries.get(i);
            int totalCyclists = entry.getCasual() + entry.getRegistered();
            String season = Utilities.toSeason(entry.getSeason());
            String weekday = Utilities.toWeekday(entry.getWeekday());
            String month = Utilities.toMonth(entry.getMonth());
            String weather = "clear"; // Placeholder for actual weather parsing if needed
            String holiday = entry.isHoliday() ? "a holiday" : "not a holiday";

            System.out.printf("The %s recorded number of cyclists was in %s, on a %s in the month of %s.%n", 
                    positions[i], season, weekday, month);
            System.out.printf("There were a total of %d cyclists. The weather was %s.%n", 
                    totalCyclists, weather);
            System.out.printf("%s was %s.%n%n", weekday, holiday);
        }
    }
}
