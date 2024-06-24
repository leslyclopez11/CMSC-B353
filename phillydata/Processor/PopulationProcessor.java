package Processor;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import Presentation.*;

public class PopulationProcessor {
    private Map<String, Integer> populationMap;

    public PopulationProcessor(String populationFile) {
        this.populationMap = new HashMap<>();
        // System.out.println("here before load");
        loadPopulation(populationFile);
    }

    private void loadPopulation(String populationFile) {
        // System.out.println("hereinPop1");
        try (BufferedReader read = new BufferedReader(new FileReader(populationFile))) {
            // System.out.println("hereinPop");
            String line;
            while ((line = read.readLine()) != null) {
                Scanner s = new Scanner(line);
                if (s.hasNext()) {
                    String zipCode = s.next().substring(0, 5);
                    if (s.hasNextInt()) {
                        int population = s.nextInt();
                        populationMap.put(zipCode, population);
                    } else {
                        System.out.println("Error: population is not an integer");
                    }
                } else {
                    System.out.println("Error: missing zip code");
                }
                
                s.close();
            }
        } catch (IOException e) {
            System.out.println("Error: Couldn't read the file");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Error: Couldn't parse through population file");
        }

    }

    public int getTotalPopulation() {
        int totalPopulation = 0;
        for (int population: populationMap.values()) {
            totalPopulation += population;
        }
        return totalPopulation;
    }

    public Map<String, Integer> getPopulationMap() {
        return populationMap;
    }
}
