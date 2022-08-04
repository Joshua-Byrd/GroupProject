package edu.upenn.cit594.ui;


import edu.upenn.cit594.processor.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {

    private Processor processor;

    public UserInterface(Processor processor) {
        this.processor = processor;
    }

    /**
     * While objects are instantiated an relationships established in main, start actually begins and runs the program.
     */
    public void start(){

        Scanner scanner = new Scanner(System.in);

        runUI(scanner);

    }

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

    public void runUI(Scanner scanner) {
        printMainMenu();

        String userInput = scanner.nextLine();

        while (userInput != "0") {
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

                default:
                    System.out.println("That is not a valid menu option. Please select from the menu above.");
                    System.out.println("\n >");
                    System.out.flush();

            }

            userInput = scanner.nextLine();
        }
    }

    /**
     * Checks that the provided date is of the form YYYY-MM-DD
     * @param date to be checked
     * @return boolean based on the validity of date
     */
    public boolean isValidDate(String date) {
       return Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", date);
    }

    /**
     * Runs the submenu for option 3: get vaccination status per capita.
     * @param scanner to get user responses
     */
    public void runVaccinationStatusSubmenu(Scanner scanner){

        Map<Integer, Double> vaccinationStatuses;

        //prompt for and retrieve vaccination status
        System.out.println("Would you like the date for full or partial vaccination status?");
        System.out.println("please enter 'full' or 'partial'");
        System.out.print(" >");

        String vaccinationResponse = scanner.nextLine();
        while (!"full".equals(vaccinationResponse) && !"partial".equals(vaccinationResponse)){
            System.out.println("That is not a valid response. Please enter 'full' or 'partial'.");
            System.out.print(" >");
            System.out.flush();
        }

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

        //get map of vaccination statuses
        if ("full".equals(vaccinationResponse)) {
            vaccinationStatuses = processor.getFullVaccinationsPerCapita(dateInput);
        } else {
            vaccinationStatuses = processor.getPartialVaccinationsPerCapita(dateInput);
        }

        //print vaccination statuses per ZIP code
        System.out.println("BEGIN OUTPUT");
        for (Map.Entry entry: vaccinationStatuses.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }

}
