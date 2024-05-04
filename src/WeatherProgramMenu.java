
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WeatherProgramMenu {

    private ArrayList<WeatherReport> weatherReports;
    private Scanner scanner;

    public WeatherProgramMenu() {
        this.weatherReports = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    //WeatherProgram Menu
    public void runMenu() throws ParseException {
        int choice;

        do {
            System.out.println("-------------- Weather Report Menu --------------\n");
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

    private void createNewWeatherReport() throws ParseException {
        WeatherData weatherData = createWeatherData();
        WeatherAPI apiService = new OpenWeatherAPI(); //implementation of WeatherAPI interface via OpenWeatherAPI class
        WeatherReport newReport = new WeatherReport(apiService, weatherData);


        newReport.displayWeatherReport();
        weatherReports.add(newReport); //add the weatherReport object to the ArrayList

    }

    private WeatherData createWeatherData() {
        //Collect and Store user input to create WeatherData object

        System.out.println("\n============ Create New Weather Data =============\n");

        String unit = "";
        boolean validChoice = false;

        while (!validChoice) {
            try {
                System.out.println("Choose temperature unit:");
                System.out.println("1. Celsius");
                System.out.println("2. Fahrenheit");
                System.out.print("Enter your choice (number only): ");

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

    private void displayWeatherReports() {
        for (int i= 0; i < weatherReports.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + weatherReports.get(i).getLocationName());
        }
    }

    private void clearWeatherReports() {
        weatherReports.clear();
        System.out.println("All weather reports cleared.\n");
    }

}
