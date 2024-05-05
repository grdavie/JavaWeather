
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WeatherProgramMenu {

    private ArrayList<WeatherReport> weatherReports;
    private Scanner scanner;

    public WeatherProgramMenu() {
        this.weatherReports = new ArrayList<>(); //Creates an ArrayList to store weatherReport objects
        this.scanner = new Scanner(System.in);
    }

    //Runs and displays the menu on the console
    public void runMenu() {
        int choice;

        do {
            System.out.println("\n-------------- WEATHER REPORT MENU --------------\n");
            System.out.println("1. New Weather Report");
            System.out.println("2. Remove Weather Report");
            System.out.println("3. Display Weather Report");
            System.out.println("4. Clear Weather Reports");
            System.out.println("5. Quit");
            System.out.println("\n--------------------------------------------------");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        createNewWeatherReport();
                        break;
                    case 2:
                        removeWeatherReport();
                        break;
                    case 3:
                        displayMenuWeatherReport();
                        break;
                    case 4:
                        clearWeatherReports();
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.\n");
                scanner.nextLine(); // Clear scanner buffer to prevent infinite loop
                choice = 0; // Reset choice to force re-entry
            }
        } while (choice != 5);

        scanner.close();
    }

    //Instantiates a WeatherData, a WeatherAPI, and a WeatherReport object
    private void createNewWeatherReport() {
        try {
            WeatherData weatherData = createWeatherData();
            WeatherAPI apiService = new OpenWeatherAPI(); // implementation of WeatherAPI interface via OpenWeatherAPI class
            WeatherReport newReport = new WeatherReport(apiService, weatherData);

            newReport.displayWeatherReport(); // automatically displays current weather and 3-day forecast info
            weatherReports.add(newReport); // add the weatherReport object to the ArrayList
        } catch (ParseException e) {
            System.out.println("\n!! Failed to create new weather report: " + e.getMessage() + " !!\n");
            System.out.println("Please enter VALID COORDINATES.\n");
        }
    }

    //Collects and Stores user input to create a valid WeatherData object
    private WeatherData createWeatherData() {

        System.out.println("\n============ Create New Weather Data =============\n");

        String unit = "";
        boolean validChoice = false;

        while (!validChoice) {
            try {
                System.out.println("Choose temperature unit:");
                System.out.println("    1. Celsius");
                System.out.println("    2. Fahrenheit");
                System.out.print("\nEnter your choice (number only): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        unit = "metric";
                        validChoice = true;
                        break;
                    case 2:
                        unit = "imperial";
                        validChoice = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer (1 or 2).");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }

        System.out.print("\nEnter a city or location name: ");
        String locationName = scanner.nextLine();

        float lat = 0.0f;
        float lon = 0.0f;

        boolean validLat = false;
        boolean validLon = false;

        while (!validLat) {
            try {
                System.out.print("Enter latitude: ");
                lat = scanner.nextFloat();
                validLat = true; // If no exception, input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid latitude input. Please enter a valid float number.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }

        while (!validLon) {
            try {
                System.out.print("Enter longitude: ");
                lon = scanner.nextFloat();
                validLon = true; // If no exception, input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid longitude input. Please enter a valid float number.");
                scanner.nextLine(); // Clear the invalid input from the scanner
            }
        }

        System.out.println("Processing request...\n");

        return new WeatherData(lon, lat, unit, locationName);
    }

    //Removes a WeatherReport object from the ArrayList using index, if available
    private void removeWeatherReport() {
        if (weatherReports.isEmpty()) {
            System.out.println("No weather reports available to remove.\n");
            return;
        }

        System.out.println("Select a weather report to remove: ");
        displayWeatherReports();

        try {
            System.out.print("\nEnter choice (number only): ");
            int index = scanner.nextInt();
            if (index > 0 && index <= weatherReports.size()) {
                weatherReports.remove(index - 1); //Adjust user-input index to zero-based index
                System.out.println("Weather report removed successfully!\n");
            } else {
                System.out.println("Invalid option. No weather report removed.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. No weather report removed.\n");
            scanner.nextLine();
        }
    }

    //Displays a specific WeatherReport from the ArrayList via index, if available
    private void displayMenuWeatherReport() {
        if (weatherReports.isEmpty()) {
            System.out.println("No weather reports available to display. \n");
            return;
        }

        System.out.println("Select a weather report to display: ");
        displayWeatherReports();

        try {
            System.out.print("\nEnter choice (number only): ");
            int index = scanner.nextInt();
            if (index > 0 && index <= weatherReports.size()) {
                WeatherReport report = weatherReports.get(index -1); //Adjust user-input index to zero-based index
                report.displayWeatherReport();
            } else {
                System.out.println("Invalid option. No weather report displayed.\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. No weather report displayed.\n");
            scanner.nextLine();
        }
    }

    //Displays a numbered list of WeatherReport objects inside the array to allow users to select an index (starts at 1)
    private void displayWeatherReports() {
        for (int i= 0; i < weatherReports.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + weatherReports.get(i).getLocationAndDateTime());
        }
    }

    //Empty the ArrayList (remove all WeatherReport objects)
    private void clearWeatherReports() {
        weatherReports.clear();
        System.out.println("All weather reports cleared.\n");
    }

}
