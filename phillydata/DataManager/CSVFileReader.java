package DataManager;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVFileReader {
    private String filePath;

    public CSVFileReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String[]> readCSV() throws IOException {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = read.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts);
            }
            return data;
        } catch (Exception e) {
            throw new IOException();
        }
    }
}
