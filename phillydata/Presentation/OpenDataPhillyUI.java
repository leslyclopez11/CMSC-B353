package Presentation;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import Processor.PopulationProcessor;
import Processor.ParkingProcessor;
import Processor.PropertyProcessor;

public class OpenDataPhillyUI {
    private PopulationProcessor populationProcessor;
    private ParkingProcessor parkingProcessor;
    private PropertyProcessor propertyProcessor;

    public OpenDataPhillyUI(PopulationProcessor populationProcessor, ParkingProcessor parkingProcessor, PropertyProcessor propertyProcessor) {
        this.populationProcessor = populationProcessor;
        this.parkingProcessor = parkingProcessor;
        this.propertyProcessor = propertyProcessor;
    }

    public void start() {
        // 
        Scanner s = new Scanner(System.in);

        while (true) {
            // System.out.println("here2");
            System.out.println("Please select an option:");
            System.out.println("1. Show total population for all ZIP codes");
            System.out.println("2. Show fines per capita for each ZIP code");
            System.out.println("3. Display average residential market value for a ZIP Code");
            System.out.println("0. Exit");

            try {
                int input = s.nextInt();
                s.nextLine();
                if (input == 1) {
                    int totalPopulation = populationProcessor.getTotalPopulation();
                    System.out.println("Total Population: " + totalPopulation);
                    break;
                } else if (input == 2) {
                    Map<String, Double> finesperCapita = parkingProcessor.getFinesMap();
                    TreeMap<String, Double> sortedFines = new TreeMap<>(finesperCapita);

                    for (Map.Entry<String, Double> entry: sortedFines.entrySet()) {
                        String zipcode = entry.getKey();
                        Double fines = entry.getValue();
                        System.out.printf("%s %.4f\n" , zipcode, fines);
                    }
                    break;
                } else if (input == 3) {
                    System.out.println("Enter a ZIP Code: ");
                    String zipCode = s.nextLine();
                    int avgMarketValue = propertyProcessor.getAverageMarketValue(zipCode);
                    System.out.println("Average residential markert value for ZIP Code " + avgMarketValue);
                    break;
                } else if (input == 4) {
                    System.out.println("Enter a ZIP Code: ");
                    String zipCode = s.nextLine();
                    int avgLivableArea = propertyProcessor.getAverageLivableArea(zipCode);
                    System.out.println("Average residential total livable area for ZIP Code " + avgLivableArea);
                    break;
                } else if (input == 5) {
                    System.out.println("Enter a ZIP Code: ");
                    String zipCode = s.nextLine();
                    int marketValueperC = propertyProcessor.getAverageMarketValuePerCapita(zipCode);
                    System.out.println("Residential market value per capita for ZIP Code " + marketValueperC);
                    break;
                } else if (input == 0) {
                    System.out.println("Exiting program...");
                    System.exit(0);
                } else {
                    System.out.println("Invalid option. Please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input");
                s.nextLine();
            }

            

        }
        s.close();
    }
}