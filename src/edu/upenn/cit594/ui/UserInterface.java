package edu.upenn.cit594.ui;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.PopulationData;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * User Interface acts as the point of interaction for the user. Provides methods to start and run the program,
 * print menus, take in and validate user input, and display desired output retrieved from the processor.
 */
public class UserInterface {
    private Set<Integer> zipCodes = new HashSet<>();
    private Processor processor;
    private final Logger logger = Logger.getInstance();

    public UserInterface(Processor processor) throws IOException {
        this.processor = processor;
    }

    /**
     * While objects are instantiated and relationships established in main,
     * start() actually begins and runs the program.
     */
    public void start() throws IOException {

        Scanner scanner = new Scanner(System.in);

        //Call methods in processor to read in the databases
        //and set database variables
        processor.setUpDatabases();

        //set up hashmap of zipcode and population
        processor.setPopulationByZipCode();

        //populate set of zipcodes for validation
        getAllZipCodes();

        //run the user interface
        runUI(scanner);

    }

    /**
     * Acts as the user interface, printing the main menu, getting user input, and running submenus
     * methods based on that input.
     * @param scanner to get user input
     */
    public void runUI(Scanner scanner) {
        printMainMenu();

        String userInput = scanner.nextLine();

        while (!"0".equals(userInput)) {
            switch (userInput) {
                case ("0"):
                    return;
                case ("1"):
                    System.out.println("BEGIN OUTPUT");
                    //changed this output to reflect the answer for Question#1538 on Ed Discussion
                    if (processor.getAvailableDataSet().size() > 0) {
                        for (String set : processor.getAvailableDataSet()) {
                            System.out.println(set);
                        }
                    }
                    System.out.println("END OUTPUT");
                    printMainMenu();
                    break;
                case ("2"):
                    if (processor.getPopulationDatabase().size() == 0) {
                        System.out.println("Missing population dataset.");
                    } else {
                        System.out.println("BEGIN OUTPUT");
                        System.out.println(processor.getTotalPopulation());
                        System.out.println("END OUTPUT");
                    }
                    printMainMenu();
                    break;
                case("3"):
                    if (processor.getPopulationDatabase().size() == 0 &&
                        processor.getCovidDatabase().size() == 0) {
                        System.out.println("Missing covid dataset and population dataset.");
                    } else if (processor.getPopulationDatabase().size() == 0) {
                        System.out.println ("Missing population dataset.");
                    } else if (processor.getCovidDatabase().size() == 0) {
                        System.out.println("Missing covid dataset.");
                    } else {
                        runVaccinationStatusSubmenu(scanner);
                    }
                    printMainMenu();
                    break;
                case("4"):
                    if (processor.getPropertyDatabase().size() == 0) {
                        System.out.println("Missing property database.");
                    } else {
                        runAvgMarketValueSubmenu(scanner);
                    }
                    printMainMenu();
                    break;
                case("5"):
                    if (processor.getPropertyDatabase().size() == 0) {
                        System.out.println("Missing property database.");
                    } else {
                        runAvgTotLivableAreaSubmenu(scanner);
                    }
                    printMainMenu();
                    break;
                case("6"):
                    if (processor.getPopulationDatabase().size() == 0 &&
                            processor.getPropertyDatabase().size() == 0) {
                        System.out.println("Missing property dataset and population dataset.");
                    } else if (processor.getPopulationDatabase().size() == 0) {
                        System.out.println ("Missing population dataset.");
                    } else if (processor.getPropertyDatabase().size() == 0) {
                        System.out.println("Missing property dataset.");
                    } else {
                        runTotMktValuePerCapitaSubmenu(scanner);
                    }
                    printMainMenu();
                    break;
                case("7"):
                    if (processor.getPopulationDatabase().size() == 0 &&
                            processor.getPropertyDatabase().size() == 0 &&
                            processor.getCovidDatabase().size() == 0) {
                        System.out.println("No available datasets.");
                    } else if (processor.getPopulationDatabase().size() == 0) {
                        System.out.println ("Missing population dataset.");
                    } else if (processor.getPropertyDatabase().size() == 0) {
                        System.out.println("Missing property dataset.");
                    } else if (processor.getCovidDatabase().size() == 0){
                        System.out.println("Missing covide dataset.");
                    } else {
                        runCustomFeatureSubmenu();
                    }
                    printMainMenu();
                    break;
                default:
                    System.out.println("\nThat is not a valid menu option. Please select from the menu above.");
                    System.out.print(" >");
                    System.out.flush();

            }

            userInput = scanner.nextLine();
        }
    }

    /*-----Menus and Submenus-----*/

    /**
     * Prints the main menu from which the user chooses their options. All submenus eventually return here.
     */
    public void printMainMenu() {
        System.out.println("0. Exit the Program");
        System.out.println("1. Show the available data sets");
        System.out.println("2. Show the total population for all ZIP Codes");
        System.out.println("3. Show the total vaccinations per capita for each ZIP Code for the specified date");
        System.out.println("4. Show the average market value for properties in a specified ZIP Code");
        System.out.println("5. Show the average total livable area for properties in a specified ZIP Code");
        System.out.println("6. Show the total market value of properties, per capita, for a specified ZIP Code");
        System.out.println("7. Show deaths per capita versus average market value of properties");
        System.out.println("Please enter a number from the menu above.");
        System.out.print(" >");
        System.out.flush();
    }

    /**
     * Runs the submenu for option 3: get vaccination status per capita, getting user input and returning
     * the requested result.
     *
     * @param scanner to get user responses
     */
    public void runVaccinationStatusSubmenu(Scanner scanner){

        Map<Integer, Double> vaccinationStatuses;
        DecimalFormat df = new DecimalFormat("#.####");

        //prompt for and retrieve vaccination status
        System.out.println("Would you like the data for full or partial vaccination status?");
        System.out.println("please enter 'full' or 'partial'");
        System.out.print(" >");
        System.out.flush();

        String vaccinationResponse = scanner.nextLine();
        while (!"full".equals(vaccinationResponse) && !"partial".equals(vaccinationResponse)){
            System.out.println("That is not a valid response. Please enter 'full' or 'partial'.");
            System.out.print(" >");
            System.out.flush();
            vaccinationResponse = scanner.nextLine();
            System.out.flush();
        }

        //log the user's response
        logger.log(System.currentTimeMillis() + " " + vaccinationResponse);

        //prompt for and get date to search for
        System.out.println("Please enter a date in the following form: YYYY-MM-DD");
        System.out.print(" >");
        System.out.flush();

        String dateInput = scanner.nextLine();
        while (!isValidDate(dateInput)) {
            System.out.println("This is not a valid date. Please enter a date in of the form YYYY-MM-DD.");
            System.out.print(" >");
            System.out.flush();
            dateInput = scanner.nextLine();
        }

        //log user's response
        logger.log(System.currentTimeMillis() + " " + dateInput);

        //get map of vaccination statuses
        if ("full".equals(vaccinationResponse)) {
            vaccinationStatuses = processor.getFullVaccinationsPerCapita(dateInput);
        } else {
            vaccinationStatuses = processor.getPartialVaccinationsPerCapita(dateInput);
        }

        //print vaccination statuses per ZIP code
        System.out.println("BEGIN OUTPUT");
        if (vaccinationStatuses.size() == 0) {
            System.out.println("0");
        } else {
            for (Map.Entry<Integer, Double> entry : vaccinationStatuses.entrySet()) {
                System.out.printf(entry.getKey() + " " + "%.6s\n", entry.getValue());
            }
        }
        System.out.println("END OUTPUT");

    }

    /**
     * Runs the submenu for option 4: Get average market value, getting user input and returning
     * the requested result.
     *
     * @param scanner to get user input
     */
    public void runAvgMarketValueSubmenu(Scanner scanner){

        System.out.println("Please enter a zipcode.");
        System.out.print(" >");
        System.out.flush();
        int userInput = scanner.nextInt();

        while(!isValidZipCode(userInput)){
            System.out.println("That is not a valid zip code. Please enter a valid zip code.");
            System.out.print(" >");
            System.out.flush();
            userInput = scanner.nextInt();
        }

        //log user's input
        logger.log(System.currentTimeMillis() + " " + userInput);

        System.out.println("BEGIN OUTPUT");
        System.out.println(processor.getAvgMarketValue(userInput));
        System.out.println("END OUTPUT");
    }

    /**
     * Runs the submenu for option 5: Get total liveable area, getting user input and returning
     * the requested result.
     *
     * @param scanner to get user input
     */
    public void runAvgTotLivableAreaSubmenu(Scanner scanner) {
        System.out.println("Please enter a zipcode.");
        System.out.print(" >");
        System.out.flush();
        int userInput = scanner.nextInt();

        while(!isValidZipCode(userInput)){
            System.out.println("That is not a valid zip code. Please enter a valid zip code.");
            System.out.print(" >");
            System.out.flush();
            userInput = scanner.nextInt();
        }

        //log user's input
        logger.log(System.currentTimeMillis() + " " + userInput);

        System.out.println("BEGIN OUTPUT");
        System.out.println(processor.getAvgTotLivableArea(userInput));
        System.out.println("END OUTPUT");
    }

    /**
     * Runs the submenu for option 6: Get total market value per capita, getting user input and returning
     * the requested result.
     *
     * @param scanner to get user input
     */
    public void runTotMktValuePerCapitaSubmenu(Scanner scanner) {
        System.out.println("Please enter a zipcode.");
        System.out.print(" >");
        System.out.flush();
        int userInput = scanner.nextInt();

        while(!isValidZipCode(userInput)){
            System.out.println("That is not a valid zip code. Please enter a valid zip code.");
            System.out.print(" >");
            System.out.flush();
            userInput = scanner.nextInt();
        }

        //log user's input
        logger.log(System.currentTimeMillis() + " " + userInput);

        System.out.println("BEGIN OUTPUT");
        System.out.println(processor.getTotalMarketValue(userInput));
        System.out.println("END OUTPUT");
    }

    public void runCustomFeatureSubmenu() {
        System.out.println("BEGIN OUTPUT");
        System.out.printf("%12s %19s %15s\n", "Zip Code", "Deaths Per Capita", "Avg Mkt Value");
        System.out.printf("%12s %19s %15s\n", "************", "*******************", "***************");
        for (Map.Entry<Integer, Double> entry: processor.getDeathsPerCapita().entrySet()) {
            int zip = entry.getKey();
            double deathsPerCapita = entry.getValue();
            int avgMktVal = processor.getAvgMarketValue(zip);


            if (deathsPerCapita != 0 && avgMktVal != 0) {
                System.out.printf("%12s %19.4f %15s\n", entry.getKey(), entry.getValue(),
                        processor.getAvgMarketValue(entry.getKey()));
            }
        }
        System.out.println("END OUTPUT");
    }

    /*-----Validation Methods-----*/

    /**
     * Checks that the provided date is of the form YYYY-MM-DD
     * @param date to be checked
     * @return boolean based on the validity of date
     */
    public boolean isValidDate(String date) {
        return Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", date);
    }

    /**
     * Iterates through databases to retrieve all zip codes for validation in other methods.
     */
    public void getAllZipCodes(){
        if (processor.getCovidDatabase().size() != 0) {
            for (CovidData c : processor.getCovidDatabase()) {
                zipCodes.add(c.getZipCode());
            }
        }
        if (processor.getPopulationDatabase().size() != 0) {
            for (PopulationData p : processor.getPopulationDatabase()) {
                zipCodes.add(p.getZipCode());
            }
        }
    }


    /**
     * Checks that the provided zip code is a valid Philadelpia area zip code.
     * @param zip code to be checked
     * @return true or false
     */
    public boolean isValidZipCode(int zip){ return zipCodes.contains(zip);}

}
