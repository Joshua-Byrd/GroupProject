package edu.upenn.cit594.ui;


import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {

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
                    for (String set : processor.getAvailableDataSet()) {
                        System.out.println(set);
                    }
                    System.out.println("END OUTPUT");
                    printMainMenu();
                    break;
                case ("2"):
                    System.out.println("BEGIN OUTPUT");
                    System.out.println(processor.getTotalPopulation());
                    System.out.println("END OUTPUT");
                    printMainMenu();
                    break;
                case("3"):
                    runVaccinationStatusSubmenu(scanner);
                    printMainMenu();
                    break;
                case("4"):
                    runAvgMarketValueSubmenu(scanner);
                    printMainMenu();
                    break;
                case("5"):
                    runAvgTotLivableAreaSubmenu(scanner);
                    printMainMenu();
                    break;
                case("6"):
                    runTotMktValuePerCapitaSubmenu(scanner);
                    printMainMenu();
                    break;
                case("7"):
                    System.out.println("Custom Feature");
                    printMainMenu();
                    break;
                default:
                    System.out.println("That is not a valid menu option. Please select from the menu above.");
                    System.out.println("\n >");
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
        System.out.println("7. Show the custom feature");
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
        System.out.println("Would you like the date for full or partial vaccination status?");
        System.out.println("please enter 'full' or 'partial'");
        System.out.print(" >");

        String vaccinationResponse = scanner.nextLine();
        while (!"full".equals(vaccinationResponse) && !"partial".equals(vaccinationResponse)){
            System.out.println("That is not a valid response. Please enter 'full' or 'partial'.");
            System.out.print(" >");
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
            System.out.println("This is not a valid date. Please enter a date is of the form YYYY-MM-DD.");
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
        for (Map.Entry<Integer, Double> entry: vaccinationStatuses.entrySet()){
            System.out.println(entry.getKey() + " " + df.format(entry.getValue()));
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
        String userInput = scanner.nextLine();

        while(!isValidZipCode(userInput)){
            System.out.println("That is not a valid zip code. Please enter a valid zip code.");
            userInput = scanner.nextLine();
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
        String userInput = scanner.nextLine();

        while(!isValidZipCode(userInput)){
            System.out.println("That is not a valid zip code. Please enter a valid zip code.");
            userInput = scanner.nextLine();
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
        String userInput = scanner.nextLine();

        while(!isValidZipCode(userInput)){
            System.out.println("That is not a valid zip code. Please enter a valid zip code.");
            userInput = scanner.nextLine();
        }

        //log user's input
        logger.log(System.currentTimeMillis() + " " + userInput);

        System.out.println("BEGIN OUTPUT");
        System.out.println(processor.getTotalMarketValue(userInput));
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
     * Checks that the provided zip code is a valid Philadelpia area zip code. How do we do this?
     * @param zip code to be checked
     * @return true or false
     */
    public boolean isValidZipCode(String zip){ return true;}

}
