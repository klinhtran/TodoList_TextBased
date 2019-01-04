package ui;

import model.*;
import model.exceptions.TooManyItemsException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;

// Use code from LittleLoggingCalculator.java

public class GroceryListTerminal {
    private static final String FILE_NAME = "list.txt";
    GroceryList list;
    Scanner scanner;
    WeatherReport weatherReport;
    ListUser user;

    public static void run() throws IOException, TooManyItemsException {
        GroceryListTerminal terminal = new GroceryListTerminal();
        terminal.runCommandLoop();
    }

    public GroceryListTerminal() throws IOException, TooManyItemsException {
        this.list = new GroceryList();
        this.list.load(FILE_NAME);
        this.scanner = new Scanner(System.in);
        weatherReport = new WeatherReport();
        user = new ListUser();
        list.addObserver(user);

    }

    public void runCommandLoop() throws IOException, TooManyItemsException {
        int n = 0;
        weatherReport.fetch();
        System.out.println(weatherReport);
        while (true) {
            System.out.println("Select [1] to add an item [2] to cross off an item [3] to show your lists [4] to quit");
            String selection = scanner.nextLine();
            System.out.println("you selected: " + selection);

            if (selection.equals("1")) {
                System.out.println("Choose section");
                Section.printAllSections();
                Section selectedSection = null;
                while (selectedSection == null) {
                    selectedSection = Section.getSectionForName(scanner.nextLine());
                }
                System.out.println("Normal item: Enter -name,quantity" +
                        "\nUrgent item: Enter *name,quantity,date(dd/mm/yy)");

                try {
                    list.addItemFromString(scanner.nextLine(), selectedSection);
                    n++;
                } catch (ParseException e) {
                    System.out.println("Couldn't parse item");
                } catch (TooManyItemsException e) {
                    System.out.println("You have too many grocery items. BUY SOMETHING and cross it off!!");
                } finally {
                    System.out.println(n + "items added");
                }

            } else if (selection.equals("2")) {
                int cross;
                list.printItemsToBuy();
                System.out.println("Which item would you like to cross off?");
                cross = new Integer(scanner.nextLine());
                list.crossOffAt(cross);
            } else if (selection.equals("3")) {
                list.printItemsToBuy();
                list.printItemsBought();
            } else if (selection.equals("4")) {
                this.list.save(FILE_NAME);
                break;
            } else {
                System.out.println("You entered an invalid number. Please choose again.");
            }



        }
    }
}
