/* The WeatherData class represents the inputs required to be able to use WeatherAPI, WeatherCondition,
and WeatherForecast
*/

public class WeatherData {

    private float lon;
    private float lat;
    private String unit;
    private String locationName;

    public WeatherData(float lon, float lat, String unit, String locationName) {
        this.lon = lon;
        this.lat = lat;
        setUnit(unit);
        this.locationName = locationName;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        //Convert the input to lowercase
        String normalizedUnit = unit.toLowerCase();

        //Validation
        if (normalizedUnit.equals("metric") || normalizedUnit.equals("imperial")) {
            this.unit = normalizedUnit;
        } else {
            //error message
            System.out.println("Error: Invalid unit. Unit must be 'metric' or 'imperial'.");
        }
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
