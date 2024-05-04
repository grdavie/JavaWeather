import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/* The OpenWeatherAPI class is an implementation of the WeatherAPI interface based on OpenWeather's Current Weather
API Call (CurrentWeatherAPI) and 5-day weather forecast API call (WeatherForecastAPI)
*/

public class OpenWeatherAPI implements WeatherAPI {

    private static final String API_KEY = OpenWeatherAPIConfig.getApiKey();
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";


    @Override
    public JSONObject parseJSONObject(String apiResponse) throws ParseException {

        //Parse the string into a JSON object
        var parser = new JSONParser();

        return (JSONObject) parser.parse(apiResponse);
    }

    @Override
    public String readAPIResponse(HttpURLConnection apiConnection) {
        try {
            //Create a StringBuilder to store the resulting JSON data
            var resultJSON = new StringBuilder();

            //Create a Scanner to read from the InputStream of the HttpURLConnection
            Scanner scanner = new Scanner(apiConnection.getInputStream());

            // Loop through each line in the response and append it to the StringBuilder
            while (scanner.hasNext()) {
                // Read and append the current line to the StringBuilder
                resultJSON.append(scanner.nextLine());
            }

            // Close the Scanner to release resources associated with it
            scanner.close();

            // Return the JSON data as a String
            return resultJSON.toString();

        } catch (IOException e) {
            // Print error message to console with details of the IOException
            System.out.println("Error reading API response: " + e.getMessage());
        }

        // Return null if there was an issue reading the response
        return null;
    }

    @Override
    public HttpURLConnection fetchCurrentWeatherAPIResponse(float lon, float lat, String unit) {

        //API call format BASE_URL+/weather?lat={lat}&lon={lon}&appid={API key}
        HttpURLConnection connection = null;

        try {
            // Construct the weather API URL
            String weatherUrl = BASE_URL + "/weather?lat=" + lat + "&lon=" + lon + "&appid="
                    + API_KEY + "&units=" + unit;

            // Create a URL object from the weather URL string
            var url = new URI(weatherUrl).toURL();

            // Open a connection to the URL
            connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            return connection;
        } catch (MalformedURLException e) {
            System.out.println("Error: Malformed URL - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: I/O Exception - " + e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("Error: URISyntaxException - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } finally {
            // Close the connection if it was opened (in case of exception)
            if (connection != null) {
                connection.disconnect();
            }
        }

        //If an exception occurred:
        return null;
    }

    @Override
    public HttpURLConnection fetchWeatherForecastAPIResponse(float lon, float lat, String unit) {

        //API call format BASE_URL+/forecast?lat={lat}&lon={lon}&appid={API key}
        HttpURLConnection connection = null;

        try {
            // Construct the forecast API URL
            String forecastUrl = BASE_URL + "/forecast?lat=" + lat + "&lon=" + lon + "&appid="
                    + API_KEY + "&units=" + unit;

            // Create a URL object from the weather URL string
            var url = new URI(forecastUrl).toURL();

            // Open a connection to the URL
            connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            return connection;
        } catch (MalformedURLException e) {
            System.out.println("Error: Malformed URL - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: I/O Exception - " + e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("Error: URISyntaxException - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } finally {
            // Close the connection if it was opened (in case of exception)
            if (connection != null) {
                connection.disconnect();
            }
        }

        //If an exception occurred:
        return null;
    }
}
