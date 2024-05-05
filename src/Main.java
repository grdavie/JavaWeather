

public class Main {
    public static void main(String[] args) {
        WeatherProgramMenu menu = new WeatherProgramMenu();

        /*
            1. New Weather Report - new instance of WeatherReport, automatically adds object to an ArrayList
            2. Remove Weather Report - removes WeatherReport object from ArrayList via index
            3. Display Weather Report - displays a WeatherReport from ArrayList via index
            4. Clear Weather Reports - removes all WeatherReport objects from ArrayList
            5. Quit - terminate program
        */

        menu.runMenu();
    }
}