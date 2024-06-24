package Processor;
import DataManager.CSVFileReader;
import DataManager.JSONFileReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;
public class PropertyProcessor {
    private String propertiesFile;
    private Map<String, Double> totalMarketValueMap;
    private Map<String, Integer> residenceMap;
    private Map<String, Double> totalLivableAreaMap;
    private Map<String, Integer> populationMap; 
    
    public PropertyProcessor(String propertiesFile, Map<String, Integer> populationMap) {
        this.propertiesFile = propertiesFile;
        this.totalMarketValueMap = new HashMap<>();
        this.residenceMap = new HashMap<>();
        this.populationMap = populationMap;
    }

    public void processProperties() {
        CSVFileReader read = new CSVFileReader(propertiesFile);

        try {
            List<String[]> propertiesD = read.readCSV();
            for (String[] columns : propertiesD) {
                if (columns.length >= 70) {
                    String zipCode = columns[69];
                    String smarketValue = columns[35];
                    String slivableArea = columns[63];
                    if (smarketValue != null && !smarketValue.isEmpty()) {
                        try {
                            double marketValue = Double.parseDouble(smarketValue);
                            if (marketValue > 0) {
                                totalMarketValueMap.put(zipCode, totalMarketValueMap.getOrDefault(zipCode, 0.0) + marketValue);
                                residenceMap.put(zipCode, residenceMap.getOrDefault(zipCode, 0) + 1);

                            }
                        } catch (NumberFormatException e) {
                            //ignore
                        }
                    }

                    if (!slivableArea.isEmpty()) {
                        try {
                            double livableArea = Double.parseDouble(slivableArea);
                            if (livableArea > 0) {
                                totalLivableAreaMap.put(zipCode, totalLivableAreaMap.getOrDefault(zipCode, 0.0) + livableArea);
                                residenceMap.put(zipCode, residenceMap.getOrDefault(zipCode, 0) + 1);

                            }
                        } catch (NumberFormatException e) {
                            //ignore
                        }
                            
                        
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the properties for the file");
        }
    }

    public int getAverageMarketValue(String zipCode) {
        double totalMarketValue = totalMarketValueMap.getOrDefault(zipCode, 0.0);
        int residenceCount = residenceMap.getOrDefault(zipCode, 0);

        if (residenceCount == 0) {
            return 0;
        }
        return (int) Math.round(totalMarketValue/residenceCount);
    }

    public int getAverageLivableArea(String zipCode) {
        double totalLivableArea = totalLivableAreaMap.getOrDefault(zipCode, 0.0);
        int residenceCount = residenceMap.getOrDefault(zipCode, 0);

        if (residenceCount == 0) {
            return 0;
        }
        return (int) Math.round(totalLivableArea/residenceCount);
    }

    public int getAverageMarketValuePerCapita(String zipCode) {
        double totalMarketValue = totalMarketValueMap.getOrDefault(zipCode, 0.0);
        int population = populationMap.getOrDefault(zipCode, 0);
        if (population == 0) {
            return 0;
        } 
        return (int) Math.round(totalMarketValue / population);
    }
}
