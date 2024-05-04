import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;

public interface WeatherAPI {

    //method to parse JSON response
    JSONObject parseJSONObject(String apiResponse) throws ParseException;

    //method to read API response using HttpURLConnection
    String readAPIResponse(HttpURLConnection apiConnection);

    //method to fetch current weather API response
    HttpURLConnection fetchCurrentWeatherAPIResponse(float lon, float lat, String unit);

    //method to fetch weather forecast API response
    HttpURLConnection fetchWeatherForecastAPIResponse(float lon, float lat, String unit);
}
