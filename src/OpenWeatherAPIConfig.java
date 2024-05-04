import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class OpenWeatherAPIConfig {

    // Method to retrieve the API key from config.properties
    public static String getApiKey() {
        try {
            Properties prop = new Properties();
            FileInputStream input = new FileInputStream("config.properties");
            prop.load(input);

            String apiKey = prop.getProperty("api.key");
            if (apiKey == null || apiKey.isEmpty()) {
                System.out.println("Error: Weather API key not found in config.properties");
                return null; // Or throw an exception: throw new RuntimeException("API key not found");
            }

            return apiKey;
        } catch (IOException e) {
            System.out.println("Error reading config.properties: " + e.getMessage());
            return null; // Or throw an exception: throw new RuntimeException("Error reading config.properties");
        }
    }
}
