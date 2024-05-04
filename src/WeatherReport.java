import org.json.simple.parser.ParseException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/* The WeatherReport class handles the display methods for current weather and 3-day weather forecast for a given
WeatherData object
*/

public class WeatherReport {

    private WeatherCondition currentWeather;
    private WeatherForecast threeDayForecast;
    private String locationName;
    private String currentDateTime;


    public WeatherReport(WeatherAPI apiService, WeatherData weatherData) throws ParseException {

        this.locationName = weatherData.getLocationName();
        this.currentWeather = new WeatherCondition(apiService, weatherData);
        this.currentDateTime = convertUnixToDate(currentWeather.getUnixTimestamp()); //convert Unit time stamp to string
        this.threeDayForecast = new WeatherForecast(apiService, weatherData);

    }

    private String convertUnixToDate(long unixTimeStamp) {
        //Convert Unix UTC timestamp (seconds) to milliseconds
        long timestampMilli = unixTimeStamp * 1000;

        //Create an Instant from the timestamp
        Instant instant = Instant.ofEpochMilli(timestampMilli);

        //Define the time zone
        ZoneId zoneId = ZoneId.systemDefault();

        //Format the Instant using a DateTimeFormatter - e.g. 2 May 2024 06:43 AM format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy hh:mm a", Locale.ENGLISH);

        return formatter.format(instant.atZone(zoneId));
    }

    private void displayCurrentWeather() {

        //Display currentWeather information on the Console
        System.out.println("\n--------------------------------------------------");
        System.out.println(locationName + ": " + currentDateTime);
        System.out.println("--------------------------------------------------");


        System.out.println("Weather Condition: " + currentWeather.getWeatherDescription());
        System.out.println("Current Temperature: " + currentWeather.getTemp() + currentWeather.getUnitSymbol());
        System.out.println("Feels like: " + currentWeather.getFeelsLikeTemp() + currentWeather.getUnitSymbol());
        System.out.println("Humidity: " + currentWeather.getHumidity() + "%");

    }

    private void displayDailyForecast(DailyForecast dailyForecast) {
        //Convert the unixTimestamp
        String forecastCurrentDateTime = convertUnixToDate(dailyForecast.getForecastUnixTimeStamp());

        //Display dailyForecast information on the console
        System.out.println("Date & Time: " + forecastCurrentDateTime);
        System.out.println("Weather Condition: " + dailyForecast.getForecastDescription());
        System.out.println("Current Temperature: " + dailyForecast.getForecastTemp() + dailyForecast.getForecastUnitSymbol());
        System.out.println("Feels like: " + dailyForecast.getForecastFeelsLikeTemp() + dailyForecast.getForecastUnitSymbol());
        System.out.println("Humidity: " + dailyForecast.getForecastHumidity() + "%");
        System.out.println();

    }

    public void displayWeatherReport() {
        displayCurrentWeather();

        //3-day forecast header
        System.out.println("\n--------------------------------------------------");
        System.out.println("Three-Day Weather Forecast (Refresh Every 3 hours)");
        System.out.println("--------------------------------------------------");

        displayDailyForecast(threeDayForecast.getForecastDay_1());
        displayDailyForecast(threeDayForecast.getForecastDay_2());
        displayDailyForecast(threeDayForecast.getForecastDay_3());

    }

    public String getLocationName() {
        return locationName + " - " + currentDateTime;
    }


}


