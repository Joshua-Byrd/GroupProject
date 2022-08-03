package edu.upenn.cit594.ui;


import edu.upenn.cit594.processor.Processor;

import java.util.Scanner;

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
        System.out.println(" >");
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
                    System.out.println("Please enter a date in the following form: YYYY-MM-DD");
                    System.out.println(" >");
                    System.out.flush();
                    String dateInput = scanner.nextLine();
                default:
                    System.out.println("That is not a valid menu option. Please select from the menu above.");
                    System.out.println("\n >");
                    System.out.flush();

            }

            userInput = scanner.nextLine();
        }
    }

}
