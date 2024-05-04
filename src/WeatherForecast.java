import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.HttpURLConnection;
import org.json.simple.parser.ParseException;

/* The WeatherForecast object uses three specific DailyForecast object to store a 3-day forecast for a given
WeatherData object. Uses the fetchWeatherForecastAPIResponse call
*/

public class WeatherForecast {

    private DailyForecast forecastDay_1;
    private DailyForecast forecastDay_2;
    private DailyForecast forecastDay_3;

    public WeatherForecast(WeatherAPI apiService, WeatherData weatherData) throws ParseException {

        if (apiService == null || weatherData == null) {
            throw new IllegalArgumentException("API service or weather data cannot be null.");
        }

        HttpURLConnection apiConnection;

        try {

            //Fetch current weather API Response
            apiConnection = apiService.fetchWeatherForecastAPIResponse(weatherData.getLon(),
                    weatherData.getLat(), weatherData.getUnit());

            //Read response, convert, and store String type
            String jsonResponse = apiService.readAPIResponse(apiConnection);

            //Parse string into a JSON object
            JSONObject jsonObject = apiService.parseJSONObject(jsonResponse);

            //Extract "list" array from jsonObject
            JSONArray forecastListArray = (JSONArray) jsonObject.get("list");

            //Set daily forecast attributes
            setDailyForecastObjects(forecastListArray, weatherData.getUnit());


        } catch (Exception e) {
            System.out.println("Error retrieving weather data: " + e.getMessage());
            throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
        }

    }

    private void setDailyForecastObjects(JSONArray forecastListArray, String unit){
        //Create Daily Forecast instances using indices: 8, 16, and 24 - the next 3 days from current date
        //at 00:00:00 timestamp

        this.forecastDay_1 = new DailyForecast(forecastListArray, 7, unit);
        this.forecastDay_2 = new DailyForecast(forecastListArray, 15, unit);
        this.forecastDay_3 = new DailyForecast(forecastListArray, 23, unit);
    }

    public DailyForecast getForecastDay_1() {
        return forecastDay_1;
    }

    public DailyForecast getForecastDay_2() {
        return forecastDay_2;
    }

    public DailyForecast getForecastDay_3() {
        return forecastDay_3;
    }
}
