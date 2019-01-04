package ui;


import model.Section;
import model.exceptions.TooManyItemsException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TooManyItemsException {
        Section.initSections();
        GroceryListTerminal.run();
    }
}