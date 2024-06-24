package Processor;
import DataManager.CSVFileReader;
import DataManager.JSONFileReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

public class ParkingProcessor {
    private Map<String, Double> finesperCapitaMap;

    public ParkingProcessor(String parkingFile, Map<String, Integer> populationMap) {
        this.finesperCapitaMap = new HashMap<>();
        if (parkingFile.equals("parking.csv")) {
            calculateFinesFromCSV(parkingFile, populationMap);
        } else if (parkingFile.equals("parking.json")) {
            calculateFinesFromJSON(parkingFile, populationMap);
        } else {
            System.out.println("Invalid file type");
        }
    }

    private void calculateFinesFromJSON(String parkingFile, Map<String, Integer> populationMap) {
        JSONFileReader jsonReader = new JSONFileReader(parkingFile);
        try {
            List<JSONObject> parkingD = jsonReader.readJson();
            Map<String, Double> totalFines = new HashMap<>();
            for (JSONObject jsonObject: parkingD) {
                String zipCode = (String) jsonObject.get("zip_code");
                double fineAmount = ((Number) jsonObject.get("fine")).doubleValue();
                String stateCode = (String) jsonObject.get("state");

                if (stateCode.equals("PA") && zipCode != null && !zipCode.isEmpty() && populationMap.containsKey(zipCode)) {
                    totalFines.put(zipCode, totalFines.getOrDefault(zipCode, 0.0) + fineAmount);
                }
                
            }

            for (String zipcode : populationMap.keySet()) {
                int population = populationMap.get(zipcode);
                double total_Fines = totalFines.getOrDefault(zipcode, 0.0);

                if (population != 0 && total_Fines != 0.0) {
                    double finesperC = total_Fines/population;
                    finesperCapitaMap.put(zipcode, finesperC);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading parking file");
        } catch (ParseException e) {
            System.out.println("Error parsing parking file");   
        }
        
        
    }

    private void calculateFinesFromCSV(String parkingFile, Map<String, Integer> populationMap) {
        CSVFileReader csvRead = new CSVFileReader(parkingFile);
        try {
            List<String[]> parkingD = csvRead.readCSV();
            Map<String, Double> totalFines = new HashMap<>();
            for (String[] column : parkingD) {
                if (column.length >= 7) {
                    String zipCode = column[6];
                    if (zipCode.isEmpty() || !populationMap.containsKey(zipCode)) {
                        continue;
                    }

                    double fineAmount = Double.parseDouble(column[1]);
                    String stateCode = column[4];

                    if (stateCode.equals("PA") && populationMap.containsKey(zipCode)) {
                        totalFines.put(zipCode, totalFines.getOrDefault(zipCode, 0.0) + fineAmount);
                    }
                }
                
                
            }

            for (String zipcode : populationMap.keySet()) {
                int population = populationMap.get(zipcode);
                double total_Fines = totalFines.getOrDefault(zipcode, 0.0);

                if (population != 0 && total_Fines != 0.0) {
                    double finesperC = total_Fines/population;
                    finesperCapitaMap.put(zipcode, finesperC);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading parking file");
        }
    }


    public Map<String, Double> getFinesMap() {
        return finesperCapitaMap;
    }
}
