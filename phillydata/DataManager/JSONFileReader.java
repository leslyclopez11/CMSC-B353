package DataManager;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import java.util.Iterator;
import org.json.simple.parser.ParseException;
public class JSONFileReader {
    private String filePath; 

    public JSONFileReader(String filePath) {
        this.filePath = filePath;
    }

    public List<JSONObject> readJson() throws IOException, ParseException {
        List<JSONObject> entries = new ArrayList<>();

        try(FileReader read = new FileReader(filePath)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(read);

            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = iterator.next();
                entries.add(jsonObject);
            }
        }

        return entries;
    }


}
