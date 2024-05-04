import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/* The DailyForecast Object is the container for the extracted weather forecast information from the forecastListArray,
JSON array extracted from the JSON object parsed via the WeatherAPI fetchWeatherForecastAPIResponse method.

The forecastListArray contains 40 forecast objects for the next 5 days with three-hour intervals. The constructor
takes a specific object using an index and stores the needed data within this object.
*/

public class DailyForecast {

    private String forecastDescription; //weather description
    private float forecastTemp;
    private float forecastFeelsLikeTemp;
    private String forecastUnitSymbol; // °C or °F
    private int forecastHumidity; // humidity as a %
    private long forecastUnixTimeStamp; // time of data calculation, unix, UTC

    //extract a forecast object by index from the "list" array of forecast objects
    public DailyForecast(JSONArray forecastListArray, int index, String unit) {

        //Retrieve forecast data from list array
        JSONObject forecastObject = (JSONObject) forecastListArray.get(index);

        //set daily forecast variables
        setForecastDescription(forecastObject);
        setForecastMainData(forecastObject);
        setForecastUnixTimeStamp(forecastObject);
        setForecastUnitSymbol(unit);

    }

    //Helper Setters
    private void setForecastDescription(JSONObject forecastObject) {
        //Retrieve "weather" data
        JSONArray weatherArray = (JSONArray) forecastObject.get("weather");

        if (weatherArray != null && !weatherArray.isEmpty()) {
            JSONObject weatherObject = (JSONObject) weatherArray.getFirst();
            this.forecastDescription = (String) weatherObject.get("description");
        }

    }

    private void setForecastMainData(JSONObject forecastObject) {
        //Retrieve "main" data
        JSONObject mainObject = (JSONObject) forecastObject.get("main");

        if (mainObject != null) {
            this.forecastTemp = ((Number) mainObject.get("temp")).floatValue();
            this.forecastFeelsLikeTemp = ((Number) mainObject.get("feels_like")).floatValue();
            this.forecastHumidity = ((Number) mainObject.get("humidity")).intValue();
        }

    }

    private void setForecastUnixTimeStamp(JSONObject forecastObject) {
        //Retrieve "dt" data
        this.forecastUnixTimeStamp = (long) forecastObject.get("dt");
    }

    private void setForecastUnitSymbol(String unit) {
        //ternary operator
        this.forecastUnitSymbol = (unit.equals("metric")) ? "°C" : "°F";
    }

    //Getters


    public String getForecastDescription() {
        return forecastDescription;
    }

    public float getForecastTemp() {
        return forecastTemp;
    }

    public float getForecastFeelsLikeTemp() {
        return forecastFeelsLikeTemp;
    }

    public String getForecastUnitSymbol() {
        return forecastUnitSymbol;
    }

    public int getForecastHumidity() {
        return forecastHumidity;
    }

    public long getForecastUnixTimeStamp() {
        return forecastUnixTimeStamp;
    }
}
