package Presentation;
import java.io.File;
import java.util.Map;
import Processor.PopulationProcessor;
import Processor.PropertyProcessor;
import Processor.ParkingProcessor;

public class OpenDataMain {
    public static void main(String[] args) {
        //checking if the number of arguments is correct 
        if (args.length != 4) {
            System.out.println("Error: Incorrect number of arguments");
            System.exit(1);
        }

        String fileType = args[0];
        // System.out.println("fileType");
        String parkingFile = args[1];
        // System.out.println("parkingType");
        String propertyFile = args[2];
        // System.out.println("propertyType");
        String populationFile = args[3];
        // System.out.println("populationType");

        if (!fileType.equals("csv") && !fileType.equals("json")) {
            System.out.println("First argument should be a CVS or JSON file");
        }

        if (fileType.equals("csv")) {
            //send it to the csv file reader
            // System.out.println("in csv");
        } else if (fileType.equals("json")) {
            //send it to the json file reader
            // System.out.println("injson");
        }
        // System.out.println("here outside");
        PopulationProcessor populationProcessor = new PopulationProcessor("Presentation/" + populationFile);
        // System.out.println("population processor");
        Map<String, Integer> populationMap = populationProcessor.getPopulationMap();
        ParkingProcessor parkingProcessor = new ParkingProcessor("Presentation/" + parkingFile, populationMap);
        PropertyProcessor propertyProcessor = new PropertyProcessor(propertyFile, populationMap);
        OpenDataPhillyUI ui = new OpenDataPhillyUI(populationProcessor, parkingProcessor, propertyProcessor);
        // System.out.println("ui");
        // System.out.println("here");
        ui.start();

    }
}
