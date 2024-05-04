import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;

/* The WeatherCondition Object is the container for the extracted current weather condition from the JSON object
parsed via the WeatherAPI fetchCurrentWeatherAPIResponse method
*/

public class WeatherCondition {

    private String weatherDescription; //Description of the current weather condition, e.g. moderate rate, etc
    private float temp;
    private float feelsLikeTemp; //human perception of current temp
    private String unitSymbol; // °C or °F
    private int humidity; // humidity as a %
    private long unixTimestamp; //Time of data calculation, unix, UTC


    public WeatherCondition(WeatherAPI apiService, WeatherData weatherData) throws ParseException {

        if (apiService == null || weatherData == null) {
            throw new IllegalArgumentException("API service or weather data cannot be null.");
        }

        HttpURLConnection apiConnection = null;

        try {

            //Fetch current weather API Response
            apiConnection = apiService.fetchCurrentWeatherAPIResponse(weatherData.getLon(),
                    weatherData.getLat(), weatherData.getUnit());

            //Read response, convert, and store String type
            String jsonResponse = apiService.readAPIResponse(apiConnection);

            //Parse string into a JSON object
            JSONObject jsonObject = apiService.parseJSONObject(jsonResponse);

            //Set weather condition variables
            setWeatherDescription(jsonObject);
            setMainData(jsonObject);
            setUnitSymbol(weatherData.getUnit());
            setUnixTimestamp(jsonObject);

        } catch (Exception e) {
            System.out.println("Error retrieving weather data: " + e.getMessage());
            throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
        }
    }

    //Helper Setters
    private void setWeatherDescription(JSONObject jsonObject) {

        //Retrieve "weather" data
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");

        if (weatherArray != null && !weatherArray.isEmpty()) {
            JSONObject weatherObject = (JSONObject) weatherArray.getFirst();
            this.weatherDescription = (String) weatherObject.get("description");
        }

    }

    private void setMainData(JSONObject jsonObject) {

        //retrieve "main" data
        JSONObject mainObject = (JSONObject) jsonObject.get("main");

        if (mainObject != null) {
            this.temp = ((Number) mainObject.get("temp")).floatValue();
            this.feelsLikeTemp = ((Number) mainObject.get("feels_like")).floatValue();
            this.humidity = ((Number) mainObject.get("humidity")).intValue();
        }
    }

    private void setUnixTimestamp(JSONObject jsonObject) {
        //Retrieve "dt" data
        this.unixTimestamp = (long) jsonObject.get("dt");
    }

    private void setUnitSymbol(String unit) {
        //ternary operator
        this.unitSymbol = (unit.equals("metric")) ? "°C" : "°F";
    }

    //Getters
    public String getWeatherDescription() {
        return weatherDescription;
    }

    public float getTemp() {
        return temp;
    }

    public float getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    public int getHumidity() {
        return humidity;
    }

    public long getUnixTimestamp() {
        return unixTimestamp;
    }
}
